package cn.jack.baselibrary.fixBug;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import dalvik.system.BaseDexClassLoader;

/**
 * Created by Jackyu
 * On 2019/3/25.
 * Describe:模拟热修复
 *          此方案的优点，更直观，适合基础叫薄弱的同学。
 *          另外：例如copyFile，combineArray是copy源码部分。
 *          个人推荐项目中的hotfixlib库方案，更规范的代码。
 *
 *          注意：该方案未经过测试，有兴趣的同学可以自己尝试。
 *
 * 热修复 三个问题：
 *      1.copyFile(srcFile, destFile)作用                                     出于安全方面的考虑
 *      2.为什么需要解压路径optimizedDirectory
 *      3.在baseapplication中初始化之后，在mainactivity位置又执行一次方法，重复。     并不是重复，若不在application去加载的话，无法实现加载修复好的dex文件
 *
 * todo 可删  热修复 查看 hotfixlib 即可
 */
public class FixDexManager {

    private Context mContext;
    private File mDexDir;

    private String TAG = "FixDexManager";

    // 单例
    private static FixDexManager mInstance;

    public static FixDexManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FixDexManager.class) {
                if (mInstance == null) {
                    mInstance = new FixDexManager(context);
                }
            }
        }
        return mInstance;
    }

    private FixDexManager(Context context) {
        this.mContext = context;
        // 获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {

        // 2. 获取下载好的补丁的 dexElement
        // 2.1 移动到系统能够访问的  dex目录下   ClassLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }

        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            Log.d(TAG, "patch [" + fixDexPath + "] has be loaded.");
            return;
        }

        copyFile(srcFile, destFile);

        // 2.2 ClassLoader读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDexFiles(fixDexFiles);
    }

    /**
     * copy file   复制源码中的内容
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexFiles = mDexDir.listFiles();

        List<File> fixDexFiles = new ArrayList<>();

        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexFile);
            }
        }

        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {

        // 1. 先获取已经运行的 dexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();
        Object applicationDexElements = getDexElementsByClassLoader(applicationClassLoader);

        //父路径:传递file路径,子路径:字符串。
        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }

        // 修复
        for (File fixDexFile : fixDexFiles) {
            // dexPath  dex路径
            // optimizedDirectory  解压路径
            // libraryPath .so文件位置
            // parent 父ClassLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),// dex路径  必须要在应用目录下的odex文件中
                    optimizedDirectory,// 解压路径
                    null,// .so文件位置
                    applicationClassLoader // 父ClassLoader
            );

            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);

            // 3. 把补丁的dexElement 插到 已经运行的 dexElement 的最前面  合并
            // applicationClassLoader 数组 合并 fixDexElements 数组

            // 3.1 合并完成
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);

            // 3.2 把合并的数组注入到原来的类中 applicationClassLoader
            injectDexElements(applicationClassLoader, applicationDexElements);
        }

    }

    /**
     * 把dexElements注入到classLoader中
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {

        // 1.先获取 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");

        // 反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        // 2. pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        dexElementsField.set(pathList, dexElements);
    }

    /**
     * 合并两个数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();               //取得一个数组的Class对象
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 从classLoader中获取 dexElements   参考源码部分
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {

        // 1.先获取 pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");

        //反射
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        // 2. pathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);

        return dexElementsField.get(pathList);
    }
}

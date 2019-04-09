package jack.cn.hotfixlib.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * created by Jack
 * date:19-4-1
 * describe:文件工具类
 */
public class FileUtils {

    /**
     *
     * @param sourceFile 源文件（来自sd卡）
     * @param targetFile 目标文件（私有）
     * @throws IOException IO异常
     */
    public static void copyFile(File sourceFile,File targetFile) throws IOException {

        //新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        //新建文件输出流并对它进行缓冲
        FileOutputStream outPut = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(outPut);

        //缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) !=-1){
            outBuff.write(b,0,len);
        }

        //刷新此缓冲区的输出流
        outBuff.flush();

        //关闭流
        input.close();
        inBuff.close();
        outPut.close();
        outBuff.close();
    }
}

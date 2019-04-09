package cn.jack.easykid.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cn.jack.easykid.R;
import cn.jack.easykid.utils.ParamsSort;
import jack.cn.hotfixlib.FixDexUtils;
import jack.cn.hotfixlib.utils.Constants;
import jack.cn.hotfixlib.utils.FileUtils;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-8
 * describe:模拟热修复hotfix实现
 */
public class Activtiy_03 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_03);

        //模拟热修复，需要读取sd卡的文件，所以高于6.0的系统需要申请运行时权限
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(Activtiy_03.this,perms[0]) == PackageManager.PERMISSION_GRANTED) {
                //具有权限
                //具体调用代码
            } else {
                //不具有sd卡读写权限权限，需要进行权限申请
                requestPermissions(perms,200);
            }
        } else {
            //当前系统小于6.0，在AndroidManifest中配置权限即可使用

        }
    }

    public void showToast(View view) {
        ParamsSort paramsSort = new ParamsSort();
        paramsSort.math(this);
    }

    public void hotFix(View view) {
        //1.通过服务器下载dex文件，如：v1.0.0有一个热修复dex包
        File sourceFile = new File(Environment.getExternalStorageDirectory(), Constants.DEX_NAME);

        //目标路径，私有目录里面的临时文件odex
        File targetFile = new File(getDir(Constants.DEX_DIR, Context.MODE_PRIVATE).getAbsolutePath()
                + File.separator + Constants.DEX_NAME);

        //如果存在，比如之前修复过的classes2.dex。清理
        if(targetFile.exists()){
            targetFile.delete();
            Toast.makeText(this,"删除已存在的dex文件",Toast.LENGTH_SHORT).show();
        }
        //2.复制修复包dex文件到app私有目录
        try {
            FileUtils.copyFile(sourceFile,targetFile);
            Toast.makeText(this,"复制dex文件完成",Toast.LENGTH_SHORT).show();
            //开始修复
            FixDexUtils.loadFixedDex(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

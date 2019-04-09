package cn.jack.easykid.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import cn.jack.baselibrary.ExceptionCrashHandler;
import jack.cn.hotfixlib.FixDexUtils;

/**
 * Created by Jackyu
 * On 2019/3/22.
 * Describe:
 *
 * 热修复细节：
 *      可以不用配置分包;
 *      attachBaseContext方法可以不添加FixDexUtils.loadFixedDex(base);
 */
//public class MyApp extends MultiDexApplication{
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionCrashHandler.getInstance().init(this);

    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//
//        //若这里不添加，再次进入的时候，之前修复好的还能生效吗？ 可以的。
//
//        FixDexUtils.loadFixedDex(base);
//    }
}

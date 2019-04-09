package cn.jack.easykid.aop;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Jackyu
 * On 2019/3/12.
 * Describe:
 *
 * 一直没有成功的一个原因，getContext方法不能私有修饰：
 * invoke-super/virtual can't be used on private method android.content.Context cn.jack.easykid.aop.CheckNetAspectJ.getContext
 *
 * 参考文章，谢谢以下几位作者，尤其是简书作者"红橙Darren",是一个非常友好，技术精湛的作者：
 * https://www.jianshu.com/p/890dd0b77ded
 * https://www.jianshu.com/p/16c5e8cdcf08
 * https://www.cnblogs.com/ganchuanpu/p/8594877.html
 *
 */
@Aspect
public class CheckNetAspectJ {

    /**
     * 找到处理的切点
     * 可以处理这个类所有的方法
     */
    @Pointcut("execution(@cn.jack.easykid.aop.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);

        if (checkNet != null) {

            // View Activity Fragment ； getThis() 当前切点方法所在的类
            Object object = joinPoint.getThis();

            // 2.判断有没有网络  怎么样获取 context?
            Context context = getContext(object);
            if (context != null) {

                if (!isNetworkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context, "请检查您的网络", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }

        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     * 注意：不能私有化。
     * @param object
     * @return
     */
    public Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

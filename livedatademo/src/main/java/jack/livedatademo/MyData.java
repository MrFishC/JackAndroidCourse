package jack.livedatademo;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-24
 * describe:
 *
 * Android的LiveData处理与Android生命周期相关的有生命周期属性的数据尤为便利。
 *
 *     LiveData是一个observable数据持有类。与常规observable不同，LiveData是生命周期感知的，
 * 这意味着它跟随其他应用程序组件（如activities, fragments, or services）的生命周期。
 * 这种感知能力确保LiveData只更新处于活跃生命周期状态的应用程序组件。
 *     LiveData与一个Observer关联，如果观察者的生命周期处于STARTED或RESUMED状态，则表示观察者处于活动状态。
 *     LiveData只通知活跃的观察者做更新。注册到LiveData对象中的不活跃的观察者则得不到数据更新的通知。
 */
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

public class MyData extends MutableLiveData<String> {
    private final String TAG = "LiveData";

    private int count = 0;
    private boolean RUN = true;

    private LongTimeWork mThread = new LongTimeWork();

    public MyData() {
        mThread.start();
    }

    @Override
    protected void onActive() {
        super.onActive();
        Log.d(TAG, "onActive");

        RUN = true;
        mThread.interrupt();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.d(TAG, "onInactive");

        RUN = false;
    }

    private class LongTimeWork extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    if (!RUN) {
                        Thread.sleep(Long.MAX_VALUE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                count++;
                postValue(String.valueOf(count));

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


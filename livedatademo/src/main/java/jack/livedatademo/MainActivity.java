package jack.livedatademo;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 1.LiveData实现基本的Android Activity/Fragment生命周期感知
 * 2.本身其持有可观察数据
 * 3.开发者可用LiveData的onActive/onInactive实现与Android生命周期解耦/耦合.进而通过LiveData的postValue
 * 或者setValue方法,通知观察者Observer数据变化(可观察的变化数据通过Observer的onChanged传导出来);
 *
 * 4.（一）使用LiveData，首先建立LiveData数据，一般继承自MutableLiveData;
 *      MutableLiveData是LiveData的子类，添加了公共方法setValue和postValue，方便开发者直接使用。setValue必须在主线程调用。postValue可以在后台线程中调用。
 *
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "LiveData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        MyObserver observer = new MyObserver();

        MyData data = new MyData();
        data.observe(this, observer);
    }

    private class MyObserver implements Observer<String> {
        @Override
        public void onChanged(@Nullable String o) {
            Toast.makeText(getApplicationContext(),String.valueOf(o),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}

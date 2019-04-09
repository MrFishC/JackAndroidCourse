package cn.jack.easykid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.jack.easykid.activity.Activtiy_03;
import cn.jack.easykid.activity.Activtiy_04;

/**
 * getAnnotation 注解内容补充： https://www.jianshu.com/p/f85e5212be55
 * getMethod 反射：https://blog.csdn.net/xjbclz/article/details/53151180
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:

                break;
            case 1:

                break;
            case 2:
                jumpActivity(Activtiy_03.class);
                break;
            case 3:
                jumpActivity(Activtiy_04.class);
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;

        }
    }

    private void jumpActivity(Class<?> clazz) {
        Intent intent = new Intent();
        intent.setClass(this,clazz);
        startActivity(intent);
    }

}

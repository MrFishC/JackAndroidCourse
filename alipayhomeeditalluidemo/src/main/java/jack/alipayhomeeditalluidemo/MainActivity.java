package jack.alipayhomeeditalluidemo;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import jack.alipayhomeeditalluidemo.tab.SynchroScrollingTab;

/**
 *  开源库  badgeview
 *
 *
 *  画出业务流程图
 *
 *  控件之间如何包裹 --->
 *
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private RecyclerView recyclerView;
    private String[] strings = new String[]{"说说", "空间", "QQ", "微信", "头条", "热点", "娱乐", "体育", "新闻", "采访", "现场", "LIVE", "世界杯", "感动中国", "CCTV", "世界报道"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        recyclerView = findViewById(R.id.recycleView);

        ListAdapter adapter = new ListAdapter(strings);
        new SynchroScrollingTab(this).setTab(recyclerView, adapter, mTabLayout, strings);

    }
}

package com.ywanhzy.demo.tab;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by BJColor on 2018/7/12.
 */

public class SynchroScrollingTab implements TabLayout.OnTabSelectedListener {
    //用来标记是否正在向最后一个滑动
    private int position;
    private int currentPostion;
    private boolean isTabSelect;
    private String[] strings;
    private TabLayout mTabLayout;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private Bm_LinearLayoutManager advertiseLinearLayoutManager;
    private Context mContext;

    public SynchroScrollingTab(Context context) {
        mContext = context;
    }

    public void setTab(RecyclerView mre, RecyclerView.Adapter madapter, TabLayout tabLayout, String[] ss) {
        if (tabLayout != null) {
            mTabLayout = tabLayout;
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        recyclerView = mre;
        adapter = madapter;
        strings = ss;
        initUI();
    }

    private void initUI() {
        if (mTabLayout != null) {
            for (int i = 0; i < strings.length; i++) {
                mTabLayout.addTab(mTabLayout.newTab());
                // TabLayout添加文本
                mTabLayout.getTabAt(i).setText(strings[i]);
            }
            mTabLayout.addOnTabSelectedListener(this);
        }

        //todo
        advertiseLinearLayoutManager = new Bm_LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(advertiseLinearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        if (adapter == null) {
            return;
        }

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mTabLayout == null) {
                    return;
                }


                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();

                if (lastVisibleItem == (totalItemCount - 1)) {
                    position = lastVisibleItem;
                } else {
                    position = manager.findFirstVisibleItemPosition();
                }

                if (isTabSelect) {//tab 选中了 就不在设置 select
                    if (position == currentPostion) {//一直到选中的tab 等于 当前滚动到的tab在设置选择
                        isTabSelect = false;
                    }
                    return;
                } else {
                    if (position == currentPostion) {//防止重复设置
                        return;
                    }
                    mTabLayout.getTabAt(position).select();
                }

            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        currentPostion = tab.getPosition();
        if (position == currentPostion) {
            return;
        }

        recyclerView.smoothScrollToPosition(currentPostion);
        isTabSelect = true;

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}

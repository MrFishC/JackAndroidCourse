package com.ywanhzy.demo.sticky;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.ywanhzy.demo.AppConfig;
import com.ywanhzy.demo.AppContext;
import com.ywanhzy.demo.R;
import com.ywanhzy.demo.adapter.MyAdapter3;
import com.ywanhzy.demo.drag.DragGridView;
import com.ywanhzy.demo.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 有些坑 请谨慎使用
 */
public class StickyActivity extends AppCompatActivity {
    /**
     * 占位tablayout，用于滑动过程中去确定实际的tablayout的位置
     */
    private TabLayout holderTabLayout;
    /**
     * 实际操作的tablayout，
     */
    private TabLayout realTabLayout;
    private CustomScrollView scrollView;
    private LinearLayout container;

    private static ArrayList<MenuEntity> menuList= new ArrayList<MenuEntity>();;

    private List<AnchorView> anchorList = new ArrayList<>();

    private static DragGridView dragGridView;

    private static AppContext appContext;

    //判读是否是scrollview主动引起的滑动，true-是，false-否，由tablayout引起的
    private boolean isScroll = true;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos = 0;
    //监听判断最后一个模块的高度，不满一屏时让最后一个模块撑满屏幕
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    private static List<MenuEntity> indexSelect = new ArrayList<MenuEntity>();
    private static MyAdapter3 adapterSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_home_more);

        dragGridView = (DragGridView) findViewById(R.id.gridview);

        holderTabLayout = findViewById(R.id.tablayout_holder);
        realTabLayout = findViewById(R.id.tablayout_real);
        scrollView = findViewById(R.id.scrollView);
        container = findViewById(R.id.container);

        appContext = (AppContext) getApplication();

        //获取设置保存到本地的菜单 ------------------------------------------------------------------------------------
        List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_USER);
        if (indexDataList != null) {
            indexSelect.clear();
            indexSelect.addAll(indexDataList);
        }

        adapterSelect = new MyAdapter3(this, appContext, indexSelect);
        dragGridView.setAdapter(adapterSelect);

        setData();
        setOther();

    }

    private void init(List<MenuEntity> indexAll) {
        menuList.clear();
        try {
            MenuEntity index = new MenuEntity();
            index.setTitle("流程审批");
            index.setId("1");
            List<MenuEntity> indexLC=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("92e44b6a-027c-4cd5-b35e-f90d29fe093f")){
                    indexLC.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("aa7f6c21-5227-4f4b-832e-e04b34a1389e")){
                    indexLC.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("a708b6d3-b5f5-439e-9544-5dc0508fc34b")){
                    indexLC.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("0c4ad7d6-cb7b-4a27-9adb-fbb82dbfe67f")){
                    indexLC.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("3d8b4e65-09b9-4731-ba97-6b3b1e317290")){
                    indexLC.add(indexAll.get(i));
                }
            }

            for (int i = 0; i < indexLC.size(); i++) {
                for (int j = 0; j < indexSelect.size(); j++) {
                    if (indexLC.get(i).getTitle().equals(indexSelect.get(j).getTitle())) {
                        indexLC.get(i).setSelect(true);
                    }
                }
            }

            index.setChilds(indexLC);
            menuList.add(index);

            MenuEntity index1 = new MenuEntity();
            index1.setTitle("绩效考核");
            index1.setId("1");

            List<MenuEntity> indexJX=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e2d3")){
                    indexJX.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd14801f")){
                    indexJX.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa91b")){
                    indexJX.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e9a")){
                    indexJX.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc853")){
                    indexJX.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa225")) {
                    indexJX.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8809")) {
                    indexJX.add(indexAll.get(i));
                }
            }

            index1.setChilds(indexJX);
            menuList.add(index1);

            MenuEntity index3 = new MenuEntity();
            index3.setTitle("绩效考核3");
            index3.setId("3");

            List<MenuEntity> indexJX3=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e211")){
                    indexJX3.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148011")){
                    indexJX3.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa911")){
                    indexJX3.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e11")){
                    indexJX3.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc811")){
                    indexJX3.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa211")) {
                    indexJX3.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8811")) {
                    indexJX3.add(indexAll.get(i));
                }
            }

            index3.setChilds(indexJX3);
            menuList.add(index3);

            MenuEntity index4 = new MenuEntity();
            index4.setTitle("绩效考核4");
            index4.setId("4");

            List<MenuEntity> indexJX4=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e222")){
                    indexJX4.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148022")){
                    indexJX4.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa922")){
                    indexJX4.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e22")){
                    indexJX4.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc822")){
                    indexJX4.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa222")) {
                    indexJX4.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8822")) {
                    indexJX4.add(indexAll.get(i));
                }
            }

            index4.setChilds(indexJX4);
            menuList.add(index4);

            MenuEntity index5 = new MenuEntity();
            index5.setTitle("绩效考核5");
            index5.setId("5");

            List<MenuEntity> indexJX5=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e233")){
                    indexJX5.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148033")){
                    indexJX5.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa933")){
                    indexJX5.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e33")){
                    indexJX5.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc833")){
                    indexJX5.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa233")) {
                    indexJX5.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8833")) {
                    indexJX5.add(indexAll.get(i));
                }
            }

            index5.setChilds(indexJX5);
            menuList.add(index5);

            MenuEntity index6 = new MenuEntity();
            index6.setTitle("绩效考核6");
            index6.setId("6");

            List<MenuEntity> indexJX6=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e244")){
                    indexJX6.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148044")){
                    indexJX6.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa944")){
                    indexJX6.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e44")){
                    indexJX6.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc844")){
                    indexJX6.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa244")) {
                    indexJX6.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8844")) {
                    indexJX6.add(indexAll.get(i));
                }
            }

            index6.setChilds(indexJX6);
            menuList.add(index6);

            MenuEntity index7 = new MenuEntity();
            index7.setTitle("绩效考核7");
            index7.setId("7");

            List<MenuEntity> indexJX7=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e255")){
                    indexJX7.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148055")){
                    indexJX7.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa955")){
                    indexJX7.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e55")){
                    indexJX7.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc855")){
                    indexJX7.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa255")) {
                    indexJX7.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8855")) {
                    indexJX7.add(indexAll.get(i));
                }
            }

            index7.setChilds(indexJX7);
            menuList.add(index7);

            MenuEntity index8 = new MenuEntity();
            index8.setTitle("绩效考核8");
            index8.setId("8");

            List<MenuEntity> indexJX8=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("ac888f31-8392-4820-9254-49b11f71e266")){
                    indexJX8.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("afce4ddf-194a-492a-b4ce-db79fd148066")){
                    indexJX8.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("8b2abd6b-18c2-4f8b-9990-b2d45f1aa966")){
                    indexJX8.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("f5462bb1-7151-4d1c-8d8e-d3653dc53e66")){
                    indexJX8.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("13673a54-fa67-4f02-aeea-e4725ffbc866")){
                    indexJX8.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("14c0f70a-5f6a-47c9-9ea4-4356773aa266")) {
                    indexJX8.add(indexAll.get(i));
                }
                if (indexAll.get(i).getId().equals("e924e4a9-0698-4624-8947-66cf883e8866")) {
                    indexJX8.add(indexAll.get(i));
                }
            }

            index8.setChilds(indexJX8);
            menuList.add(index8);


            MenuEntity index2 = new MenuEntity();
            index2.setTitle("其他");
            index2.setId("2");

            List<MenuEntity> indexQT=new ArrayList<MenuEntity>();
            for (int i = 0; i < indexAll.size(); i++) {
                if(indexAll.get(i).getId().equals("1437cd9c-4595-46cb-8fde-e866e43f0825")){
                    indexQT.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("1cd85fc6-0b69-4f04-aa79-883c6ba8649e")){
                    indexQT.add(indexAll.get(i));
                }
                if(indexAll.get(i).getId().equals("a4f08830-adaa-4412-9adf-55b9e773118e")){
                    indexQT.add(indexAll.get(i));
                }
            }

            for (int i = 0; i < indexQT.size(); i++) {
                for (int j = 0; j < indexSelect.size(); j++) {
                    if (indexQT.get(i).getTitle().equals(indexSelect.get(j).getTitle())) {
                        indexQT.get(i).setSelect(true);
                    }
                }
            }
            index2.setChilds(indexQT);
            menuList.add(index2);

            //tablayout下方的内容区域
            for (int i = 0; i < menuList.size(); i++) {
                MenuEntity menuEntity = menuList.get(i);
                //tablayout
                holderTabLayout.addTab(holderTabLayout.newTab().setText(menuEntity.getTitle()));
                realTabLayout.addTab(realTabLayout.newTab().setText(menuEntity.getTitle()));

                AnchorView anchorView = new AnchorView(this);
                anchorView.setAnchorTxt(menuEntity.getTitle());
                anchorView.setContentTxt(menuEntity.getChilds());           //更改 AnchorView

                anchorList.add(anchorView);
                container.addView(anchorView);
            }

        }catch (Exception e){

        }
    }

    private void setData() {
        List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_All);
        init(indexDataList);
    }

    private void setOther() {

        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {

                Log.i(" onTabSelected ", " onGlobalLayout: " );

                //计算让最后一个view高度撑满屏幕
                int screenH = getScreenHeight();
                int statusBarH = getStatusBarHeight(StickyActivity.this);
                int tabH = holderTabLayout.getHeight();
//                int lastH = screenH - statusBarH - tabH - 16 * 3;         //干扰项
                int lastH = screenH - statusBarH - tabH + 1;                // 1 的作用： very good o
                AnchorView anchorView = anchorList.get(anchorList.size() - 1);
                if (anchorView.getHeight() < lastH) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = lastH;
                    anchorView.setLayoutParams(params);
                }

                //一开始让实际的tablayout 移动到 占位的tablayout处，覆盖占位的tablayout
                realTabLayout.setTranslationY(holderTabLayout.getTop());
                realTabLayout.setVisibility(View.VISIBLE);
                container.getViewTreeObserver().removeOnGlobalLayoutListener(listener);

            }
        };

        container.getViewTreeObserver().addOnGlobalLayoutListener(listener);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });

        //监听scrollview滑动
        scrollView.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {

                Log.i(" onTabSelected ", " onScrollChanged: " );

                //根据滑动的距离y(不断变化的) 和 holderTabLayout距离父布局顶部的距离(这个距离是固定的)对比，
                //当y < holderTabLayout.getTop()时，holderTabLayout 仍在屏幕内，realTabLayout不断移动holderTabLayout.getTop()距离，覆盖holderTabLayout
                //当y > holderTabLayout.getTop()时，holderTabLayout 移出，realTabLayout不断移动y，相对的停留在顶部，看上去是静止的

                int translation = Math.max(y, holderTabLayout.getTop());

                realTabLayout.setTranslationY(translation);

                if (isScroll) {

                    Log.i(" onTabSelected ", " isScroll: " );

                    // 1dp = 3px

                    for (int i = menuList.size() - 1; i >= 0; i--) {
                        //需要y减去顶部内容区域的高度(具体看项目的高度，这里demo写死的200dp)
                        if (y > anchorList.get(i).getTop()) {

                            setScrollPos(i);
                            break;
                        }
                    }
                }

            }
        });

        //实际的tablayout的点击切换
        realTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isScroll = false;
                int pos = tab.getPosition();
                int top = anchorList.get(pos).getTop();

                Log.i(" onTabSelected ", " 1: " + top);
                Log.i(" onTabSelected ", " 2: " + dragGridView.getHeight());

                //同样这里滑动要加上顶部内容区域的高度(这里写死的高度)
//                scrollView.smoothScrollTo(0, top + 200 * 3);

                if(pos == anchorList.size() - 1){
                    top = top + dragGridView.getHeight();
                }
                scrollView.smoothScrollTo(0, top );

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void setScrollPos(int newPos) {

        Log.i(" onTabSelected ", " setScrollPos: " );

        if (lastPos != newPos) {
            realTabLayout.setScrollPosition(newPos, 0, true);
        }

        lastPos = newPos;

    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

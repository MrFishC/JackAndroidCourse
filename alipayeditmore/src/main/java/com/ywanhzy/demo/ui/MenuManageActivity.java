package com.ywanhzy.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ywanhzy.demo.AppConfig;
import com.ywanhzy.demo.AppContext;
import com.ywanhzy.demo.ListAdapter;
import com.ywanhzy.demo.R;
import com.ywanhzy.demo.adapter.MyAdapter;
import com.ywanhzy.demo.drag.DragGridView;
import com.ywanhzy.demo.entity.MenuEntity;
import com.ywanhzy.demo.tab.SynchroScrollingTab;

import java.util.ArrayList;
import java.util.List;

public class MenuManageActivity extends Activity {

	private static DragGridView dragGridView;
	private static MyAdapter adapterSelect;
	private static ArrayList<MenuEntity> menuList= new ArrayList<MenuEntity>();;
	private static ListAdapter menuParentAdapter;

	private TabLayout mTabLayout;
	private RecyclerView recyclerView;

	private TextView tv_top_title;
	private static AppContext appContext;
	private static List<MenuEntity> indexSelect = new ArrayList<MenuEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_manage);

		appContext = (AppContext) getApplication();
		dragGridView = (DragGridView) findViewById(R.id.gridview);

		initView();
		initData();

	}

	private void initView() {

		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		recyclerView = findViewById(R.id.recycleView);

		// TODO Auto-generated method stub
		tv_top_title = (TextView) findViewById(R.id.tv_item_cate_name);

		tv_top_title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuManageActivity.this,EditActivity.class);
				startActivity(intent);
			}
		});

		//获取设置保存到本地的菜单
		List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_USER);
		if (indexDataList != null) {
			indexSelect.clear();
			indexSelect.addAll(indexDataList);
		}

		adapterSelect = new MyAdapter(this, appContext, indexSelect);
		dragGridView.setAdapter(adapterSelect);
	}

	private void initData() {
		// TODO Auto-generated method stub
		List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_All);
		init(indexDataList);

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

			String[] strings = new String[menuList.size()];
			for (int i = 0; i < menuList.size(); i++) {
				strings[i] = menuList.get(i).getTitle();
			}

			menuParentAdapter = new ListAdapter(MenuManageActivity.this, menuList);
			new SynchroScrollingTab(this).setTab(recyclerView, menuParentAdapter, mTabLayout, strings);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

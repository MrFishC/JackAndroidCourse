package com.ywanhzy.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.ywanhzy.demo.AppConfig;
import com.ywanhzy.demo.AppContext;
import com.ywanhzy.demo.ListAdapter;
import com.ywanhzy.demo.R;
import com.ywanhzy.demo.adapter.MyEditAdapter;
import com.ywanhzy.demo.drag.DragCallback;
import com.ywanhzy.demo.drag.DragGridView;
import com.ywanhzy.demo.entity.MenuEntity;
import com.ywanhzy.demo.tab.SynchroScrollingTab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends Activity {

	private static DragGridView dragGridView;
	private static MyEditAdapter adapterSelect;
	private static ArrayList<MenuEntity> menuList= new ArrayList<MenuEntity>();;
	private static ListAdapter menuParentAdapter;
	private LinearLayout ll_top_back;

	private TabLayout mTabLayout;
	private RecyclerView recyclerView;

	private LinearLayout ll_top_sure;
	private TextView tv_top_title;
	private TextView tv_top_sure;
	private static AppContext appContext;
	private TextView tv_drag_tip;
	private static List<MenuEntity> indexSelect = new ArrayList<MenuEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_manage_edit);

		appContext = (AppContext) getApplication();
		dragGridView = (DragGridView) findViewById(R.id.gridview);

		initView();
		initData();

		setOther();

		ll_top_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tv_top_sure.getText().toString().equals("管理")) {
					tv_top_sure.setText("完成");
					adapterSelect.setEdit();
					if(menuParentAdapter!=null){
						menuParentAdapter.setEdit();
					}
					tv_drag_tip.setVisibility(View.VISIBLE);
				} else {
					tv_top_sure.setText("管理");
					tv_drag_tip.setVisibility(View.GONE);
					adapterSelect.endEdit();
					if(menuParentAdapter!=null){
						menuParentAdapter.endEdit();
					}
					postMenu();
				}
			}
		});
	}

	private ViewTreeObserver.OnGlobalLayoutListener listener;
	private void setOther() {

		final View view = findViewById(R.id.rl_top);

		listener = new ViewTreeObserver.OnGlobalLayoutListener() {
			@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onGlobalLayout() {

				//计算让最后一个view高度撑满屏幕
				int screenH = getResources().getDisplayMetrics().heightPixels;
				int statusBarH = getStatusBarHeight(EditActivity.this);
				int tabH = mTabLayout.getHeight();
				int lastH = screenH - statusBarH - tabH - dragGridView.getHeight() - view.getHeight()  + 1  ;// 1 的作用： very good o

				LinearLayout view1 = (LinearLayout) recyclerView.getChildAt(menuList.size()-1);

				if (view1.getHeight() < lastH) {
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					params.height = lastH;
					view1.setLayoutParams(params);
				}

				recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);

			}
		};

		recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
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

	protected void postMenu() {
		// TODO Auto-generated method stub
		List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_USER_TEMP);
		String key = AppConfig.KEY_USER;
		appContext.saveObject((Serializable) indexDataList, key);
	}

	private void initView() {

		mTabLayout = (TabLayout) findViewById(R.id.tabs);
		recyclerView = findViewById(R.id.recycleView);

		// TODO Auto-generated method stub
		ll_top_back = (LinearLayout) findViewById(R.id.ll_top_back);
		ll_top_sure = (LinearLayout) findViewById(R.id.ll_top_sure);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_sure = (TextView) findViewById(R.id.tv_top_sure);
		tv_top_title.setText("全部应用");
		tv_top_sure.setText("管理");
		tv_top_sure.setVisibility(View.VISIBLE);
		
		tv_drag_tip= (TextView) findViewById(R.id.tv_drag_tip);

		ll_top_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		//获取设置保存到本地的菜单
		List<MenuEntity> indexDataList = (List<MenuEntity>) appContext.readObject(AppConfig.KEY_USER);
		if (indexDataList != null) {
			indexSelect.clear();
			indexSelect.addAll(indexDataList);
		}

		adapterSelect = new MyEditAdapter(this, appContext, indexSelect);
		dragGridView.setAdapter(adapterSelect);

		dragGridView.setDragCallback(new DragCallback() {
			@Override
			public void startDrag(int position) {
				Logger.i("start drag at ", ""+ position);
			}
			@Override
			public void endDrag(int position) {
				Logger.i("end drag at " ,""+ position);
			}
		});
		dragGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.e("setOnItemClickListener",adapterSelect.getEditStatue()+"");
				if(!adapterSelect.getEditStatue()){
					//dragGridView.clicked(position);
					MenuEntity cateModel = indexSelect.get(position);
					initUrl(cateModel);
				}
			}
		});
		dragGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				if (tv_top_sure.getText().toString().equals("管理")) {
					tv_top_sure.setText("完成");
					adapterSelect.setEdit();
					if(menuParentAdapter!=null){
						menuParentAdapter.setEdit();
					}
					tv_drag_tip.setVisibility(View.VISIBLE);
				}
				dragGridView.startDrag(position);
				return false;
			}
		});

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

			menuParentAdapter = new ListAdapter(EditActivity.this, menuList);
			new SynchroScrollingTab(this).setTab(recyclerView, menuParentAdapter, mTabLayout, strings);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void initUrl(MenuEntity cateModel) {
		// TODO Auto-generated method stub
		if (tv_top_sure.getText().toString().equals("管理")) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			String title = cateModel.getTitle();
			String strId = cateModel.getId();
			Toast.makeText(EditActivity.this,title,Toast.LENGTH_SHORT).show();
		}
	}

	public  void DelMeun(MenuEntity indexData,int position) {
		// TODO Auto-generated method stub
		for (int i = 0; i < menuList.size(); i++) {
			for (int k = 0; k < menuList.get(i).getChilds().size(); k++) {
				if (menuList.get(i).getChilds().get(k).getTitle().equals(indexData.getTitle())) {
					menuList.get(i).getChilds().get(k).setSelect(false);
				}
			}
		}
		if(menuParentAdapter!=null){
			menuParentAdapter.notifyDataSetChanged();
		}
		adapterSelect.notifyDataSetChanged();
	}

	public static void AddMenu(MenuEntity menuEntity) {
		// TODO Auto-generated method stub
		indexSelect.add(menuEntity);
		String key = AppConfig.KEY_USER_TEMP;
		appContext.saveObject((Serializable) indexSelect, key);
		
		for (int i = 0; i < menuList.size(); i++) {
			for (int k = 0; k < menuList.get(i).getChilds().size(); k++) {
				if (menuList.get(i).getChilds().get(k).getTitle().equals(menuEntity.getTitle())) {
					menuList.get(i).getChilds().get(k).setSelect(true);
				}
			}
		}
		menuParentAdapter.notifyDataSetChanged();
		adapterSelect.notifyDataSetChanged();
	}

}

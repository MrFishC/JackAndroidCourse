package com.ywanhzy.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ywanhzy.demo.R;
import com.ywanhzy.demo.entity.MenuEntity;

import java.util.List;

public class MenuChildAdapter1 extends BaseAdapter {

	private List<MenuEntity> menuList;
	private Context context;

	public MenuChildAdapter1(Context context, List<MenuEntity> list) {
		this.context = context;
		this.menuList = list;
	}

	@Override
	public int getCount() {
		return menuList.size();
	}

	@Override
	public Object getItem(int position) {
		return menuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodler viewHodler = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.items_cate_child, null);
			viewHodler = new ViewHodler();
			viewHodler.tv_item_cate_child_name = (TextView) convertView.findViewById(R.id.tv_item_cate_child_name);

			viewHodler.deleteImg = (ImageView) convertView.findViewById(R.id.delete_img);
			viewHodler.iconImg = (ImageView) convertView.findViewById(R.id.icon_img);

			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		final MenuEntity menuEntity = menuList.get(position);

		viewHodler.deleteImg.setVisibility(View.GONE);

		//获取资源图片
		int drawableId = context.getResources().getIdentifier(menuEntity.getIco(),"mipmap", context.getPackageName());
		viewHodler.iconImg.setImageResource(drawableId);

		viewHodler.tv_item_cate_child_name.setText(menuEntity.getTitle());
		return convertView;
	}

	private class ViewHodler {
		private TextView tv_item_cate_child_name;
		private ImageView deleteImg, iconImg;
	}

}

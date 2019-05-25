package com.ywanhzy.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.ywanhzy.demo.adapter.MenuChildAdapter;
import com.ywanhzy.demo.entity.MenuEntity;
import com.ywanhzy.demo.widget.MyGridView;
import java.util.List;

/**
 * Created by @vitovalov on 30/9/15.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener{

    List<MenuEntity> mListData;
    private Context context;

    boolean IsEdit;

    public ListAdapter(List<MenuEntity> mListData) {
        this.mListData = mListData;
    }

    public ListAdapter(Context c,List<MenuEntity> addressProvincesList2) {
        context = c;
        this.mListData = addressProvincesList2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //更换布局  RecyclerView嵌套RecyclerView
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_cate_grid_child,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.groupViewHolde.tv_item_cate_name.setText(mListData.get(i).getTitle());

        myViewHolder.toolbarGrid.setNumColumns(4);// 设置每行列数
        myViewHolder.toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
        myViewHolder.adapter = new MenuChildAdapter(context, mListData.get(i).getChilds(),IsEdit);
        myViewHolder.toolbarGrid.setAdapter(myViewHolder.adapter);          // 设置菜单Adapter
        myViewHolder.toolbarGrid.setOnItemClickListener(this);
        myViewHolder.toolbarGrid.setOnItemLongClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MyGridView toolbarGrid;
        MenuChildAdapter adapter;
        GroupViewHolde groupViewHolde = null;

        public MyViewHolder(View itemView) {
            super(itemView);
            toolbarGrid = (MyGridView) itemView.findViewById(R.id.gv_toolbar);
            groupViewHolde = new GroupViewHolde();
            groupViewHolde.tv_item_cate_name = (TextView) itemView.findViewById(R.id.tv_item_cate_name);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class GroupViewHolde {
        TextView tv_item_cate_name;
        ImageView iv_items_cate_pic;
    }

    public void setEdit() {
        IsEdit = true;
        notifyDataSetChanged();
    }

    public void endEdit() {
        IsEdit = false;
        notifyDataSetChanged();
    }

}



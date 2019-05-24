package com.ywanhzy.demo.sticky;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ywanhzy.demo.R;
import com.ywanhzy.demo.adapter.MenuChildAdapter1;
import com.ywanhzy.demo.entity.MenuEntity;
import com.ywanhzy.demo.widget.MyGridView;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */

public class AnchorView extends LinearLayout {
    private TextView tvAnchor;
    private MyGridView toolbarGrid;
    private MenuChildAdapter1 adapter;
    private Context mContext;

    public AnchorView(Context context) {
        this(context, null);

    }

    public AnchorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnchorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_anchor, this, true);
        tvAnchor = view.findViewById(R.id.tv_anchor);
        toolbarGrid = (MyGridView) view.findViewById(R.id.gv_toolbar);

//        Random random = new Random();
//        int r = random.nextInt(256);
//        int g = random.nextInt(256);
//        int b = random.nextInt(256);
//        tvContent.setBackgroundColor(Color.rgb(r, g, b));

    }

    public void setAnchorTxt(String txt) {
        tvAnchor.setText(txt);
    }

    public void setContentTxt(List<MenuEntity> mListData) {
        //数据源 contentTxt
        //设置  toolbarGrid 的适配器
        toolbarGrid.setNumColumns(4);
//        toolbarGrid.setGravity(Gravity.CENTER);
        adapter = new MenuChildAdapter1(mContext, mListData);
        toolbarGrid.setAdapter(adapter);          // 设置菜单Adapter
//        toolbarGrid.setOnItemClickListener(this);
//        toolbarGrid.setOnItemLongClickListener(this);

    }
}

package cn.jack.easykid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import cn.jack.baselibrary.base.BaseActivity;
import cn.jack.easykid.R;
import jack.cn.statelayoutlib.StateLayout;

/**
 * created by Jack
 * email:yucrun@163.com
 * date:19-4-9
 * describe:使用状态布局的activity基类,需要使用状态布局的activity继承该基类
 */
public abstract class BaseStateActivity extends BaseActivity{

    private StateLayout mStateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //重写父类的setContentView方法
        setContentView(R.layout.activity_state_base);

        mStateLayout.setStateLayout(StateLayout.STATE_SUCCESS, getLayoutView());

        //执行顺序,父类调用了
        //initView();
        //initData();

        // 事件监听
        initListener();
    }

    /**
     * 初始化监听       非抽象方法
     **/
    protected void initListener() {
        mStateLayout.getStateView(StateLayout.STATE_ERROR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

}

package jack.cn.statelayoutlib;//package com.demo.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 设计：
 * 一 属性
 * 三种状态常量   public static final int
 * 一个当前状态   private int currentState
 * 不同的状态对应用的View xxView yyView zzView
 * <p>
 * 二 方法
 * 指定当前状态      void setState(int state)
 * 获取当前状态      int getState()
 * 指定某种状态对应的View  void setStateView(int state,View v)
 * 获取某种状态对应的View  View getStateView(int state)
 * 指定某种状态对应的Layout  void setStateLayout(int state, int layout)
 * <p>
 * 三 功能更强大
 * 如果没有指定，有默认显示（textview显示一些文字）
 * 可以通过xml属性来进行设置, 需要自定义样式，在两参数的构造中获取自定义样式，完成
 */

public class StateLayout extends FrameLayout {

    private static final int STATE_NONE = -1;

    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_SUCCESS = 3;
    public static final int STATE_EMPTY = 4;

    public static final int STATE_COUNT = 4;

    @IntDef({STATE_NONE, STATE_LOADING, STATE_ERROR, STATE_SUCCESS, STATE_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public StateLayout(Context context) {
        this(context, null);
    }

    int[] mLayouts = new int[STATE_COUNT];

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
            for (int i = 0; i < STATE_COUNT; i++) {
                mLayouts[i] = typedArray.getResourceId(i, 0);
            }
            typedArray.recycle();
        }
        for (int i = 0; i < STATE_COUNT; i++) {
            setStateLayout(i, mLayouts[i]);
        }
    }

    public void setStateLayout(@State int state, @LayoutRes int layout) {
        View view = null;

        if (layout == 0) {
            // 根据判断 加载状态布局  文字描述 体验 不咋好
            //todo 在布局中没有声明 attrs中定义的 布局,则 layout会为0
            if (state == STATE_LOADING) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_loding, this, false);
            } else if (state == STATE_ERROR) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_error, this, false);
            } else if (state == STATE_EMPTY) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this, false);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_empty, this, false);
            }
        } else {
            view = LayoutInflater.from(getContext()).inflate(layout, this, false);
        }
        setStateView(state, view);
    }

    View[] mViews = new View[STATE_COUNT];

    public void setStateView(@State int state, @NonNull View view) {
        if (mViews[state] != null) {
            removeView(mViews[state]);
        }
        mViews[state] = view;
        addView(mViews[state]);
        if (currentState == state) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public View getStateView(@State int state) {
        return mViews[state];
    }

    int currentState = STATE_NONE;

    public void setState(@State int state) {
        for (int i = 0; i < mViews.length; i++) {
            mViews[i].setVisibility(i == state ? View.VISIBLE : View.GONE);
        }
        currentState = state;
    }

    @State
    public int getState() {
        return currentState;
    }
}

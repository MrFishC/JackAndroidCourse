package com.ywanhzy.demo.tab;

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.DisplayMetrics;

/**
 * Created by BJColor on 2018/7/11.
 */

public class Bm_LinearSmoothScroller extends LinearSmoothScroller {

    public Bm_LinearSmoothScroller(Context context) {
        super(context);
    }

    /**
     * @param viewStart      RecyclerView的top位置
     * @param viewEnd        RecyclerView的Bottom位置
     * @param boxStart       item的top位置
     * @param boxEnd         item的bottom位置
     * @param snapPreference 滑动方向的识别
     * @return
     */
    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        return boxStart - viewStart;//返回的就是我们item置顶需要的偏移量
    }

    /**
     * 此方法返回滚动每1px需要的时间,可以用来控制滚动速度
     * 即如果返回2ms，则每滚动1000px，需要2秒钟
     *
     * @param displayMetrics
     * @return
     */
    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//        float v = 20f / displayMetrics.densityDpi;
        return super.calculateSpeedPerPixel(displayMetrics);
    }
}
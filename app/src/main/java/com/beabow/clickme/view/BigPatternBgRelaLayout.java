package com.beabow.clickme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

import com.beabow.clickme.tools.LogUtils;

/**
 * 创建者： lx
 * 创建时间：2016/6/14 10:28
 * 描述: 大图模式下的根布局
 */
public class BigPatternBgRelaLayout extends RelativeLayout {
    public BigPatternBgRelaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
        LogUtils.d("111111onInterceptTouchEvent : ");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d("111  onTouchEvent:"+getChildAt(2).onTouchEvent(event));
        if(getChildAt(2).onTouchEvent(event)){
           return true;
        }else{
            return true;
        }
    }
}

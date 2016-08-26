package com.beabow.clickme.view;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.UiUtils;

/**
 * 创建者： lx
 * 创建时间：2016/6/10 12:41
 * 描述: TODO
 */
public class MainViewPager extends ViewPager {

    private int startX;
    private Context context;
    private final int touchSlop;

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (UiUtils.getBigPattern()) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.d("onInterceptTouchEvent:"+UiUtils.getBigPattern() + "");
        if (UiUtils.getBigPattern()) {
            return false;
        } else {
//            switch (ev.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    startX = (int) ev.getRawX();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    int moveX = (int) ev.getRawX();
//                    LogUtils.d(Math.abs(moveX - startX) + "----" + (Math.abs(moveX - startX) > touchSlop));
//                    if (Math.abs(moveX - startX) > touchSlop) {
//                        return false;
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    break;
//            }
            return super.onInterceptTouchEvent(ev);
        }
    }
}

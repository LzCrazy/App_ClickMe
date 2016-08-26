package com.beabow.clickme.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.beabow.clickme.MyApplication;
import com.beabow.clickme.tools.LogUtils;

/**
 * 创建者： lx
 * 创建时间：2016/6/10 12:41
 * 描述: 大图模式下的ViewPager
 */
public class BigPatternViewPager extends ViewPager {
    public BigPatternViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.d("2222 onTouchEvent:"+super.onTouchEvent(ev));
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
       // getParent().requestDisallowInterceptTouchEvent(true);
//        if (MyApplication.getBigPattern()) {
//            //LogUtils.d("请求爹不要拦截我。 我要滑动。。111111");
//            getParent().requestDisallowInterceptTouchEvent(true);
//        } else {
//           // LogUtils.d("请求爹拦截我。。。我不想滑动了。2222222。。。");
//            getParent().requestDisallowInterceptTouchEvent(false);
//        }
        LogUtils.d("2222 dispatchTouchEvent:"+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }
}

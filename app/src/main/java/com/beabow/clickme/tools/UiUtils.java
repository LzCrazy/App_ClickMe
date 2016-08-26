package com.beabow.clickme.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.beabow.clickme.MyApplication;
import com.bumptech.glide.load.engine.Resource;

/**
 * 创建者： lx
 * 创建时间：2016/5/30 10:41
 * 描述: TODO
 */
public class UiUtils {
    private static Toast mToast = null;

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 得到Resouce对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 单例吐司
     */
    public static void showToast(Context context, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 安全的执行一个任务
     */
    public static void postTaskSafely(Runnable runnable) {
        int currThreadId = android.os.Process.myTid();
        if (currThreadId == MyApplication.getMainTreadId()) {
            runnable.run();
        } else {
            MyApplication.getHandler().post(runnable);
        }
    }

    /**
     * dip转换px
     */
    public static int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dp(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取屏幕宽
     */
    public static DisplayMetrics getScreenWidth() {
        WindowManager manager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取大图模式
     * false: 列表显示
     */
    public static boolean getBigPattern() {
        return MyApplication.getBigPattern();
    }

    /**
     * 设置大图模式
     * false: 列表显示
     */
    public static void setBigPattern(boolean bigPattern) {
        MyApplication.setBigPattern(bigPattern);
    }

    /**
     * 记录刷新次数。每个界面默认是0.刷新一次+1
     */
    private static int refreshCount = 0;

    public static int getRefreshCount() {
        return refreshCount;
    }

    public static void setRefreshCount(int refreshCount) {
        UiUtils.refreshCount = refreshCount;
    }

}

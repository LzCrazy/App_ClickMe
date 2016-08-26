package com.beabow.clickme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.LoginUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/5/30 9:03
 * 描述: TODO
 */
public class MyApplication extends Application {
    public static RequestQueue sQueues;
    public static Context sContext;
    public static Handler sHandler;
    public static OkHttpManager sOkHttpManager;
    private static int mMainTreadId;
    private static LoginUtils loginUtils;

    public static boolean getBigPattern() {
        return bigPattern;
    }

    public static void setBigPattern(boolean isBigPattern) {
        MyApplication.bigPattern = isBigPattern;
    }

    private static boolean bigPattern = false;

    @Override
    public void onCreate() {
        //上下文
        sContext = getApplicationContext();
        // 主线程Id
        mMainTreadId = android.os.Process.myTid();
        sHandler = new Handler();
        super.onCreate();
        sQueues = Volley.newRequestQueue(this);
        sOkHttpManager = OkHttpManager.getInstance();

    }

    public static OkHttpManager getOkHttpManager() {
        return sOkHttpManager;
    }

    public static RequestQueue getHttpQueues() {
        return sQueues;
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler() {
        return sHandler;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainTreadId() {
        return mMainTreadId;
    }

    public static LoginUtils getLoginUtils() {
        if (loginUtils == null) {
            loginUtils = new LoginUtils();
        }
        return loginUtils;
    }

    /**
     * 管理所有Activity
     */
    public static List<Activity> activityList;

    public static synchronized void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public static void clearActivity() {
        if (activityList != null && !activityList.isEmpty()) {
            for (Activity activity : activityList) {
                activity.finish();
            }
            activityList.clear();
            activityList = null;
        }
    }
}

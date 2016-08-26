package com.beabow.clickme.tools;

import android.content.Context;

/**
 * 创建者： lx
 * 创建时间：2016/6/3 9:14
 * 描述: TODO
 */
public class LoginUtils {
    private boolean isLogined = false;
    /**
     * 判断是否登录了
     */
    public boolean getIsLogined(Context context) {
        if (!isLogined) {
            isLogined = (Boolean) SPUtils.getData(context, SPUtils.LOGIN_STATE, false);
        }
        return isLogined;
    }
    /**
     * 设置用户登录了
     */
    public void setIsLogin(Context context, boolean isLogined) {
        SPUtils.saveData(context, SPUtils.LOGIN_STATE, isLogined);
        this.isLogined = isLogined;
    }
}

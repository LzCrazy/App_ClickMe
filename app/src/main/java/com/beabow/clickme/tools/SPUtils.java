package com.beabow.clickme.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


public class SPUtils {
    // 存储的sharedpreferences文件名
    private static final String CONFIG_NAME = "clickme"; //配置的名字
    public static final String LOGIN_STATE="login_state"; //登录状态
    public static final String USER_ID="userID"; //用户ID
    public static final String USER_NAME="username";//用户名
    public static final String PASSWORD="password";//密码
    public static final String FACE="face"; //头像地址
    public static final String KEY="key";
    public static final String EMAIL="email";
    public static final String PHONE="phone";
    public static final String RECORD_CLICK="record_click";  //记录被点击的技师
    public static final String IS_EMPTY="is_empty";  //记录当前分类是否是空的状态

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param
     */
    public static void saveData(Context context, String key, Object value) {
        String type = value.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if ("String".equals(type)) {
            edit.putString(key, (String) value);
        } else if ("Integer".equals(type)) {
            edit.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            edit.putBoolean(key, (Boolean) value);
        } else if ("Float".equals(type)) {
            edit.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            edit.putLong(key, (Long) value);
        }else if ("Long".equals(type)) {
            edit.putLong(key, (Long) value);
        }else if ("HashSet".equals(type)) {
            edit.putStringSet(key, (Set<String>) value);
        }
        edit.commit();
    }


    /**
     * 从文件中读取数据
     * 
     * @param context
     * @param key
     * @param defValue 获取不到返回的默认值
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                CONFIG_NAME, Context.MODE_PRIVATE);
        if (defValue == null) {
            return sharedPreferences.getStringSet(key, null);
        }
        String type = defValue.getClass().getSimpleName();
        // defValue为为默认值，如果当前获取不到数据就返回它
        if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }
        return null;
    }
}

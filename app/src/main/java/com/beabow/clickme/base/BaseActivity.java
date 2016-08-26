package com.beabow.clickme.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.beabow.clickme.MyApplication;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.LogUtils;

public abstract class BaseActivity extends Activity implements View.OnClickListener {

    public Context context;
    public OkHttpManager okManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        okManager = MyApplication.getOkHttpManager();
        //全局集合管理栈
        MyApplication.addActivity(this);
        setContentView(getContentView());
        initData();
    }
    /**
     * 查找view
     */
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    protected abstract int getContentView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {}

    /**
     * 左上角的点击事件
     */
    public void onBackClose(View view) {
       // LogUtils.d("BaseActivity 。。。左上角的点击事件。");
        finish();
    }
    @Override
    public void onBackPressed() {
       // LogUtils.d("BaseActivity 。。。返回。");
        finish();
        return;
    }

    @Override
    protected void onDestroy() {
       // MyApplication.clearActivity();
        super.onDestroy();
    }
}

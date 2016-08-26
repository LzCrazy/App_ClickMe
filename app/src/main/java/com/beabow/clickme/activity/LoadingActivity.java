package com.beabow.clickme.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;

public class LoadingActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        getWindow().setFormat(PixelFormat.RGBA_8888);
        return R.layout.activity_loading;
    }

    @Override
    protected void initData() {
        MyApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty((String) SPUtils.getData(context, SPUtils.KEY, ""))) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }
}

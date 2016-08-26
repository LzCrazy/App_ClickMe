package com.beabow.clickme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.LoginUtils;
import com.beabow.clickme.tools.SPUtils;

public class MySetActivity extends BaseActivity {

    private RelativeLayout mRlUpdatePw, mRlFeedback,mRlAboutMe;
    private TextView  mTvName;
    //private ImageView mIvBack;
    private Button mBtSubmit;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_set;
    }

    @Override
    protected void initData() {
        mRlUpdatePw = findView(R.id.id_rl_updatepw);
        mRlFeedback = findView(R.id.id_rl_idea);
        mRlAboutMe = findView(R.id.id_rl_aboutme);
        mBtSubmit = findView(R.id.id_submit);
        //mIvBack = findView(R.id.iv_back);
        mTvName = findView(R.id.id_tv_name);
        mTvName.setText((String) SPUtils.getData(this, SPUtils.USER_NAME, ""));
//        mIvBack.setClickable(true);
        mRlUpdatePw.setOnClickListener(this); //修改密码
        mRlFeedback.setOnClickListener(this); //意见反馈
        mRlAboutMe.setOnClickListener(this); //关于我们
        mBtSubmit.setOnClickListener(this); //退出登录
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.id_rl_updatepw:
                intent = new Intent(context, UpdatePwActivity.class);
                startActivity(intent);
                break;
            case R.id.id_rl_idea:
                intent = new Intent(context, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.id_submit:
                setResult(888);
                finish();
                break;
            case R.id.id_rl_aboutme:
                intent = new Intent(context, AboutMeWebActivity.class);
                startActivity(intent);
                break;
        }
    }
}

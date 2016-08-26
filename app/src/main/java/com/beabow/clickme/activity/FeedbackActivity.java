package com.beabow.clickme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.domain.BaseJson;
import com.beabow.clickme.domain.BaseJsonList;
import com.beabow.clickme.domain.ListBean;
import com.beabow.clickme.domain.LoginBean;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.LoginUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;
import com.beabow.clickme.view.AutoSearchEditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class FeedbackActivity extends BaseActivity implements AutoSearchEditText.OnAutoSearchListener {


    private Button mBtSubmit;
    private AutoSearchEditText mAutoEditText;
    private TextView mTvMsg, mTvBack;

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initData() {

        mBtSubmit = findView(R.id.id_submit);
        mAutoEditText = findView(R.id.id_auto_et);
        mTvMsg = findView(R.id.id_tv_msg);
        mTvBack = findView(R.id.id_tv_back);
        mTvBack.setClickable(true);
        mBtSubmit.setOnClickListener(this);
        mAutoEditText.setOnAutoSearchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_submit:
                String content = mAutoEditText.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    UiUtils.showToast(this, "请输入内容");
                    return;
                }
                String key = (String) SPUtils.getData(this, SPUtils.KEY, "");
                HashMap<String, String> params = new HashMap();
                params.put("version", "1");
                params.put("key", key);
                params.put("content", content);
                okManager.postAsync(this, Config.FEEDBACK, params, new OkHttpManager.DataCallBack() {
                    @Override
                    public void requestSuccess(String json) throws Exception {
                        BaseJsonList<ListBean> bean = JsonUtils.toDataList(json, ListBean.class);
                        if (bean.getSuccess().equals(Config.ONE)) {
                            UiUtils.showToast(context, bean.getMsg());
                            finish();
                        } else if (bean.getSuccess().equals(Config.TWO)) {
                            UiUtils.showToast(context, bean.getMsg());
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("isFinish", 1);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }
                });
                break;
        }
    }

    @Override
    public void onSearch(String s) {
        mTvMsg.setText(s.length() + "/400");
    }
}

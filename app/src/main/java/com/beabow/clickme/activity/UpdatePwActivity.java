package com.beabow.clickme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.domain.BaseJson;
import com.beabow.clickme.domain.LoginBean;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.LoginUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class UpdatePwActivity extends BaseActivity {

    private EditText mEtOriginalPw, mEtNewPw, mEtConfimPw;
    private Button mBtSubmit;
    private TextView mTvBack;
    @Override
    protected int getContentView() {
        return R.layout.activity_update_pw;
    }

    @Override
    protected void initData() {
        mEtOriginalPw = findView(R.id.id_et_originalPw);
        mEtNewPw = findView(R.id.id_et_newPw);
        mEtConfimPw = findView(R.id.id_et_confimPw);
        mBtSubmit = findView(R.id.id_submit);
        mTvBack = findView(R.id.id_back);
        mTvBack.setClickable(true);
        mBtSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_submit:
                String originalPw = mEtOriginalPw.getText().toString();
                if (TextUtils.isEmpty(originalPw)) {
                    UiUtils.showToast(context, "原始密码不能为空");
                    return;
                }
                String newPw = mEtNewPw.getText().toString();
                String confimPw = mEtConfimPw.getText().toString();
                if (TextUtils.isEmpty(newPw) || TextUtils.isEmpty(confimPw)) {
                    UiUtils.showToast(context, "密码不能为空");
                    return;
                }
                if (!newPw.equals(confimPw)) {
                    UiUtils.showToast(context, "两次密码输入不一样");
                    return;
                }
                String key = (String) SPUtils.getData(this, SPUtils.KEY, "");
                HashMap<String, String> params = new HashMap<>();
                params.put("version", "1");
                params.put("key", key);
                params.put("password", originalPw);
                params.put("newpassword", newPw);
                okManager.postAsync(this, Config.UPDATE_PW, params, new OkHttpManager.DataCallBack() {
                    @Override
                    public void requestSuccess(String json) throws Exception {
                        BaseJson<LoginBean> bean = JsonUtils.toDataObj(json, LoginBean.class);
                        if (bean.getSuccess().equals(Config.ONE)) {
                            LoginBean data = bean.getData();
                            SPUtils.saveData(context, SPUtils.USER_ID, data.getUser_id());  //ID
                            SPUtils.saveData(context, SPUtils.USER_NAME, data.getUser_name());//名字
                            SPUtils.saveData(context, SPUtils.PHONE, data.getPhone());//电话
                            SPUtils.saveData(context, SPUtils.FACE, data.getFace());//头像地址
                            SPUtils.saveData(context, SPUtils.KEY, data.getKey());//key
                            SPUtils.saveData(context, SPUtils.PASSWORD, data.getKey());//密码
                            UiUtils.showToast(context, bean.getMsg());
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            //修改密码成功
                            MyApplication.clearActivity();
//                            finish();
                        } else if (bean.getSuccess().equals(Config.ZERO)) {
                            UiUtils.showToast(context, bean.getMsg());
                            //key过期
                        } else if (bean.getSuccess().equals(Config.TWO)) {
                            UiUtils.showToast(context, bean.getMsg());
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            MyApplication.clearActivity();
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }
                });
                break;
        }
    }

//    /**
//     * 左上角的点击事件
//     */
//    public void onBackClose(View view) {
//        LogUtils.d("左上角的点击事件1111111。。。。。。");
//        finish();
//    }
}

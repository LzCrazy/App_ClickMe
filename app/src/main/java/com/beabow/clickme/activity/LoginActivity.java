package com.beabow.clickme.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.domain.BaseJson;
import com.beabow.clickme.domain.LoginBean;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LoginUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class LoginActivity extends BaseActivity {

    //账号 密码
    private EditText mEtAccount, mEtPw;
    //登录
    private Button mBtSubmit;
    private View view1,view2;
    private int isFinish = 0;
    private int isClearActivity=0;
    @Override
    protected int getContentView() {
        if (getIntent() != null) {
            isFinish = getIntent().getIntExtra("isFinish", 0);
            isClearActivity = getIntent().getIntExtra("isClearActivity", 0);
        }
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        if(isClearActivity!=0){

        }

        mEtAccount = findView(R.id.id_et_account);
        mEtPw = findView(R.id.id_et_pw);
        mBtSubmit = findView(R.id.id_submit);
        view1 = findView(R.id.id_view1);
        view2 = findView(R.id.id_view2);

        view1.getBackground().setAlpha(153);
        view2.getBackground().setAlpha(153);
        mBtSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
               switch (v.getId()) {
            case R.id.id_submit:
                String userName = mEtAccount.getText().toString();
                String password = mEtPw.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    UiUtils.showToast(this, "账号不能为空哦!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    UiUtils.showToast(this, "密码不能为空哦!");
                    return;
                }
                HashMap<String, String> params = new HashMap();
                //版本
                params.put("version", "1");
                //账号
                params.put("user_name", userName);
                //密码
                params.put("password", password);
                //网络请求
                okManager.postAsync(this, Config.LOGIN, params, new OkHttpManager.DataCallBack() {
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
                            if (isFinish == 1) {
                                finish();
                                return;
                            }
                            MyApplication.clearActivity();
                            LoginUtils loginUtils = MyApplication.getLoginUtils();
                            loginUtils.setIsLogin(context, true);//全局标记用户已经登录
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            //finish();
                        } else if (bean.getSuccess().equals(Config.ZERO)) {
                            UiUtils.showToast(context, bean.getMsg());
                        }
                    }

                    @Override
                    public void requestFailure(Request request, IOException e) {

                    }
                });
                break;
        }
    }
}

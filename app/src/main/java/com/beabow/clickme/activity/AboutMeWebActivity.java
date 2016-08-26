package com.beabow.clickme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;

public class AboutMeWebActivity extends BaseActivity {


    private String url="http://xiangzw.cn/click_me/api.php?s=Mobile/index&id=1";
    private WebView mWebView;
    @Override
    protected int getContentView() {
        return R.layout.activity_about_me_web;
    }

    @Override
    protected void initData() {
        mWebView=findView(R.id.id_webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);// 打开页面时， 自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setSupportZoom(false);// 用于设置webview放大
        mWebView.loadUrl(url);
    }
}

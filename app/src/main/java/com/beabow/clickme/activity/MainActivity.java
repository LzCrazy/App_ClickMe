package com.beabow.clickme.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.adapter.HomePageAdapter;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.base.BaseFragment;
import com.beabow.clickme.base.LoadingPager;
import com.beabow.clickme.domain.BaseJsonList;
import com.beabow.clickme.domain.NavigationBean;
import com.beabow.clickme.factory.FragmentFactory;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.LoginUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mIvSet, mIvPattern;
    // private HomePageAdapterCache mPageAdapter;
    private HomePageAdapter mPageAdapter;

    /**
     * 所有分类名字
     */
    private List<String> mTitles;
    /**
     * 所有分类的ID
     */
    private List<String> mIdList;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        LogUtils.d("MainActivity。。。。。。onCreate()....");
        mTabLayout = (TabLayout) findViewById(R.id.id_tabs);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mIvSet = (ImageView) findViewById(R.id.iv_set);
        mIvPattern = (ImageView) findViewById(R.id.iv_pattern);
        mIvSet.setOnClickListener(this);
        mIvPattern.setOnClickListener(this);

        initServerData();


    }

    private void initServerData() {
        String key = (String) SPUtils.getData(this, SPUtils.KEY, "");
        HashMap<String, String> params = new HashMap<>();
        params.put("version", Config.ONE);
        params.put("key", key);
        okManager.postAsync(this, Config.NAVIGATION, params, new OkHttpManager.DataCallBack() {
            @Override
            public void requestSuccess(String json) throws Exception {
                BaseJsonList<NavigationBean> bean = JsonUtils.toDataList(json, NavigationBean.class);
                if (bean.getSuccess().equals(Config.ONE)) {
                    mTitles = new ArrayList<>();
                    mIdList = new ArrayList<>();
                    List<NavigationBean> data = bean.getData();
                    //把所有标签都添加到集合里
                    for (NavigationBean bean2 : data) {
                        mTitles.add(bean2.getTitle());
                        mIdList.add(bean2.getId());
                    }
                    initTabLayout();
                } else if (bean.getSuccess().equals(Config.TWO)) {
                    UiUtils.showToast(context, bean.getMsg());
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    LogUtils.d("1111111111111111111。。key过期了。。。。。。");
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {

            }
        });
    }

    /**
     * 创建Tab指示器
     */
    private void initTabLayout() {
        if (!mTitles.isEmpty()) {
            for (int i = 0; i < mTitles.size(); i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(i)));
            }
            initViewPager();
        }


    }

    /**
     * 初始化Viewpager
     */
    private void initViewPager() {
        mPageAdapter = new HomePageAdapter(getFragmentManager(), mTitles);
        mViewPager.setAdapter(mPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPageAdapter);
        initListener();
        //默认加载第一个界面的数据
        mViewPager.setCurrentItem(0,false);
        BaseFragment fragment = FragmentFactory.getFragment(0);
        LoadingPager loadingPager = fragment.getLoadingPager();
        LogUtils.d("MainActivity。。。。。initViewPager。。。底层View为空吗？。。" + (loadingPager == null)+"--frar:"+(fragment==null));
        if (loadingPager != null) {
            loadingPager.loadData(mIdList.get(0));
        }
    }

    private void initListener() {
        /**
         * 设置TabLayout监听
         */
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mViewPager.setCurrentItem(position,false);
                lastPosition = position;
                BaseFragment fragment = FragmentFactory.getFragment(position);
                LogUtils.d("tab:" + position + "---" + tab.getText() + "--底层view为空？" + (fragment.getLoadingPager() == null));
                if (fragment != null) {
                    //显示大图模式还是列表模式
                    LoadingPager pager =
                            fragment.getLoadingPager();
                    if (pager != null) {
                        pager.loadData(mIdList.get(position));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private int lastPosition = 2;




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //设置页面
            case R.id.iv_set:
                Intent intent = new Intent(this, MySetActivity.class);
                startActivityForResult(intent, 666);
                break;
            //大图模式
            case R.id.iv_pattern:
                if ((Boolean) SPUtils.getData(this, SPUtils.IS_EMPTY, false)) {
                    return;
                }
                boolean bigPattern = UiUtils.getBigPattern();
                LogUtils.d("大图模式。。。。" + bigPattern);
                if (bigPattern) {
                    mIvPattern.setImageResource(R.mipmap.iv_pattern);
                } else {
                    mIvPattern.setImageResource(R.mipmap.iv_pattern_press);
                }
               UiUtils.setBigPattern(!bigPattern);
                BaseFragment fragment = FragmentFactory.getFragment(lastPosition);
                fragment.show();
               // mViewPager.setFocusable(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 666 && resultCode == 888) {
            gotoLoginActivity();
            finish();
            LogUtils.d("退出登录啦。。。。");
        }
    }

    @Override
    protected void onDestroy() {
        FragmentFactory.clearFragment();
        super.onDestroy();
        LogUtils.d("MainActivity。。。。onDestroy。。。。。");
    }

    private void gotoLoginActivity() {
        LoginUtils loginUtils = MyApplication.getLoginUtils();
        loginUtils.setIsLogin(context, false);//全局标记用户未登录
        SPUtils.saveData(context, SPUtils.USER_ID, "");  //ID
        SPUtils.saveData(context, SPUtils.USER_NAME, "");//名字
        SPUtils.saveData(context, SPUtils.PHONE, "");//电话
        SPUtils.saveData(context, SPUtils.FACE, "");//头像地址
        SPUtils.saveData(context, SPUtils.KEY, "");//key
        SPUtils.saveData(context, SPUtils.PASSWORD, "");//密码
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}

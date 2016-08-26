package com.beabow.clickme.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.base.BaseActivity;
import com.beabow.clickme.dialog.ServerDialogFrag;
import com.beabow.clickme.domain.BaseJsonList;
import com.beabow.clickme.domain.ListBean;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import okhttp3.Request;

/**
 * 技师详情页面
 */
public class TechnicianDetailActivity extends BaseActivity implements ServerDialogFrag.OnClickListen {

    private ViewPager mViewPager;
    private String mName, mJobNumber, mAge, mBust, mWaistline, mHipline, mID;
    private ArrayList<String> mPics;
    private MyPagerAdapter mAdapter;
    private int position;
    /**
     * 存放所有的滑动图片
     */
    private List<ImageView> mImgList;

    @Override
    protected int getContentView() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            mName = extras.getString("name");
            mID = extras.getString("id");
            position = extras.getInt("position");
            mJobNumber = extras.getString("job_number");
            mAge = extras.getString("age");
            mBust = extras.getString("bust");
            mWaistline = extras.getString("waistline");
            mHipline = extras.getString("hipline");
            mPics = extras.getStringArrayList("pics");
        }
        return R.layout.activity_technician_detail;
    }

    private TextView mTvName, mTvAge, mTvSize, mTvCurrCount;
    private ImageView mIvClick;

    @Override
    protected void initData() {
        mViewPager = findView(R.id.id_viewpager);
        mTvName = findView(R.id.id_tv_name);
        mTvAge = findView(R.id.id_tv_age);
        mTvSize = findView(R.id.id_tv_count);
        mTvCurrCount = findView(R.id.id_tv_currCount);
        mIvClick = findView(R.id.id_iv_like);
        mIvClick.setOnClickListener(this);

        /**
         * 创建所有的滑动图片
         */
        mImgList = new ArrayList<>();
        for (int i = 0; i < mPics.size(); i++) {
            ImageView img = new ImageView(context);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(params);
            mImgList.add(img);
        }
        mAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mTvName.setText("昵称： " + mName + "   " + "工号：" + mJobNumber);
        mTvAge.setText("年龄： " + mAge + "   " + "三围：" + mBust + "  " + mWaistline + "  " + mHipline);
        mTvCurrCount.setText("1");
        mTvSize.setText("/" + mPics.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTvCurrCount.setText(position + 1 + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImgList.get(position);
            Glide.with(context).load(mPics.get(position)).crossFade().into(mImgList.get(position));

            container.addView(imageView);
            return imageView;
        }

        /**
         * 销毁page
         * position： 当前需要销毁第几个page
         * object:当前需要销毁的page
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 只是一个标示是否点击过该技师
     */
    private int flag;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_like:
                if (flag != 0) {
                    OkHttpManager okManager = MyApplication.getOkHttpManager();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("version", Config.ONE);
                    map.put("technician_id", mID);
                    okManager.postAsync(context, Config.UNCLICKME, map, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestSuccess(String json) throws Exception {
                            BaseJsonList<ListBean> bean = JsonUtils.toDataList(json, ListBean.class);
                            if (bean.getSuccess().equals(Config.ONE)) {
                                mIvClick.setImageResource(R.mipmap.iv_like_click);
                                UiUtils.showToast(context, bean.getMsg());
                                flag = 0;
                                //移除被点击的技师
                                HashSet<String> spSet = (HashSet<String>) SPUtils.getData(context, SPUtils.RECORD_CLICK, "");
                                if (spSet != null) {
                                    if (spSet.contains(mID)) {
                                        spSet.remove(mID);
                                        SPUtils.saveData(context, SPUtils.RECORD_CLICK, spSet);
                                    }
                                }
                            } else {
                                //请求失败，返回服务器提示
                                ServerDialogFrag frag = (ServerDialogFrag) getFragmentManager().findFragmentByTag("serverDialog");
                                frag.getDialog().dismiss();
                                UiUtils.showToast(context, bean.getMsg());
                            }
                        }

                        @Override
                        public void requestFailure(Request request, IOException e) {
                        }
                    });
                } else { //说明没点击过，弹出是否点击的吐司
                    ServerDialogFrag dialog = ServerDialogFrag.newInstance(mName, mJobNumber);
                    dialog.setOnClickListen(this);
                    dialog.show(getFragmentManager(), "serverDialog");
                }
                break;
        }
    }

    /**
     * 点击弹出确认对话框的回调
     */
    @Override
    public void onConfirmClick(final int flag) {
        //LogUtils.d("onConfirmClick。。。。" + flag + "----" + mJobNumber);
        OkHttpManager okManager = MyApplication.getOkHttpManager();
        HashMap<String, String> map = new HashMap<>();
        map.put("version", Config.ONE);
        map.put("technician_id", mID);
        okManager.postAsync(context, Config.CHOOSE_TECHNICIAN, map, new OkHttpManager.DataCallBack() {
            @Override
            public void requestSuccess(String json) throws Exception {
                BaseJsonList<ListBean> bean = JsonUtils.toDataList(json, ListBean.class);
                if (bean.getSuccess().equals(Config.ONE)) {
                    ServerDialogFrag frag = (ServerDialogFrag) getFragmentManager().findFragmentByTag("serverDialog");
                    mIvClick.setImageResource(R.mipmap.iv_like_clickafter);
                    frag.getDialog().dismiss();
                    TechnicianDetailActivity.this.flag = flag;
                    UiUtils.showToast(context, bean.getMsg());

                    //保存被点击的技师
                    HashSet<String> spSet = (HashSet<String>) SPUtils.getData(context, SPUtils.RECORD_CLICK, "");
                    if (spSet == null) {
                        spSet = new HashSet<>();
                    }
                    spSet = new HashSet<>(spSet);
                    spSet.add(mID);
                    SPUtils.saveData(context, SPUtils.RECORD_CLICK, spSet);
                } else {
                    //请求失败，返回服务器提示
                    ServerDialogFrag frag = (ServerDialogFrag) getFragmentManager().findFragmentByTag("serverDialog");
                    frag.getDialog().dismiss();
                    UiUtils.showToast(context, bean.getMsg());
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
            }
        });
    }

    /**
     * 左上角的点击事件
     */
    public void onBackClose(View view) {
        if (flag != 0) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(888, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        if (flag != 0) {
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(888, intent);
        }
        finish();
        return;
    }
}

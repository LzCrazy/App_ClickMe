package com.beabow.clickme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beabow.clickme.Config;
import com.beabow.clickme.MyApplication;
import com.beabow.clickme.R;
import com.beabow.clickme.activity.LoginActivity;
import com.beabow.clickme.activity.TechnicianDetailActivity;
import com.beabow.clickme.adapter.BigPatternPageAdapter;
import com.beabow.clickme.adapter.OneAdapter;
import com.beabow.clickme.anim.ZoomOutPageTransformer;
import com.beabow.clickme.base.BaseFragment;
import com.beabow.clickme.base.LoadingPager.LoadedResult;
import com.beabow.clickme.domain.BaseJsonList;
import com.beabow.clickme.domain.PageListBean;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.JsonUtils;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.SPUtils;
import com.beabow.clickme.tools.UiUtils;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 创建者： lx
 * 创建时间：2016/5/31 10:05
 * 描述: TODO
 */
public class backupOneFragment extends BaseFragment
        implements ViewPager.OnPageChangeListener, BigPatternPageAdapter.OnImgClickListener {
    private TextView mTvSize, mTvCurrCount;
    private ViewPager mViewPager;
    private RelativeLayout mRlHide;

    private List<PageListBean> mDatas = new ArrayList<>();
    private String mId = ""; //分类ID
    private int index = 0;
    private PtrClassicFrameLayout mPftFrameLayout;
    private RecyclerView mRecyclerView;
    private OneAdapter oneAdapter;
    private RecyclerAdapterWithHF mAdapter;
    private BigPatternPageAdapter mBigPagerAdapter;

    @Override
    public LoadedResult initData(String id) {
        mId = id;
        initServerData(id);
        return LoadedResult.SUCCESS;
    }

    /**
     * 初始化服务器数据
     */
    private void initServerData(String id) {
        String key = (String) SPUtils.getData(mActivity, SPUtils.KEY, "");
        Map<String, String> params = new HashMap<>();
        params.put("version", Config.ONE);
        params.put("key", key);
        params.put("category_id", id);
        okManager.postAsync(mActivity, Config.PAGE_INFO, params, new OkHttpManager.DataCallBack() {
            @Override
            public void requestSuccess(String json) throws Exception {
                BaseJsonList<PageListBean> bean = JsonUtils.toDataList(json, PageListBean.class);
                //LogUtils.d("请求成功。1111。index===" + index + "---页面ID===" + mId + "  成功码a==" + bean.getSuccess());
                if (bean.getSuccess().equals(Config.ONE)) {
                    if (mDatas != null && !mDatas.isEmpty()) {
                        mDatas.clear();
                    }
                    List<PageListBean> data = bean.getData();
                    mDatas.addAll(data);
                    //如果是0 就初始化界面
                    if (index == 0) {
                        getLoadingPager().refreshUI(2); //刷新成功状态，会调用initSuccessView方法
                    } else { //否则直接刷新适配器
                        refreshData();
                    }
                } else if (bean.getSuccess().equals(Config.TWO)) {
                    UiUtils.showToast(mActivity, bean.getMsg());
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                getLoadingPager().refreshUI(1);
            }
        });
    }


    @Override
    public View initSuccessView(ViewGroup parent) {
        View view = mActivity.getLayoutInflater().inflate(R.layout.one_fragment, parent, false);
        mPftFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.id_ptr_framelayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        mTvCurrCount = (TextView) view.findViewById(R.id.id_tv_currCount);
        mTvSize = (TextView) view.findViewById(R.id.id_tv_count);
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        mRlHide = (RelativeLayout) view.findViewById(R.id.id_rl_hide);

       //设置大图模式viewPager的宽
        mTvSize.setText("/ " + mDatas.size());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
        int width = UiUtils.getScreenWidth().widthPixels - UiUtils.dp2px(80);
        params.width = width;
        mViewPager.setLayoutParams(params);
        //设置大图模式的数据
        mBigPagerAdapter = new BigPatternPageAdapter(mActivity, mDatas);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mBigPagerAdapter);
        mBigPagerAdapter.setOnImgClickListener(this);


        //设置列表模式的数据
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        oneAdapter = new OneAdapter(mDatas, R.layout.one_item);
        mAdapter = new RecyclerAdapterWithHF(oneAdapter);
        initPullRefresh();
        show();

        return view;
    }

    /**
     * 是否显示大图模式
     */
    public void show() {
        if (MyApplication.getBigPattern()) {
            mPftFrameLayout.setVisibility(View.GONE);
            mRlHide.setVisibility(View.VISIBLE);
            mTvSize.setText("/ " + mDatas.size());
            mBigPagerAdapter.notifyDataSetChanged();
        } else {
            mRlHide.setVisibility(View.GONE);
            mPftFrameLayout.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initPullRefresh() {
        mPftFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //一进来就弹出自动刷新
                mPftFrameLayout.autoRefresh(true);
            }
        }, 100);
        //下拉刷新
        mPftFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (index != 0) {
                    index++;
                    initServerData(mId);
                    return;
                }
                mPftFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index++;
                        mRecyclerView.setAdapter(mAdapter);
                        mPftFrameLayout.refreshComplete();
                    }
                }, 1000);
            }
        });

        /**
         * 点击事件
         */
        mAdapter.setOnItemClickListener(new RecyclerAdapterWithHF.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerAdapterWithHF adapter, RecyclerView.ViewHolder holder, int position) {
                gotoDetailActivity(position);
            }
        });
    }

    private void gotoDetailActivity(int position) {
        ArrayList<String> picUrls = new ArrayList<>();
        PageListBean bean = mDatas.get(position);
        for (String picUrl : bean.getBanner()) {
            picUrls.add(picUrl);
        }
        Intent intent = new Intent(mActivity, TechnicianDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", bean.getGoods_name()); //名字
        bundle.putInt("position", position); //点击位置
        bundle.putString("job_number", bean.getJob_number()); //工号
        bundle.putString("id", bean.getId()); //工号
        bundle.putString("age", bean.getAge()); //年龄
        bundle.putString("bust", bean.getBust()); //胸围
        bundle.putString("waistline", bean.getWaistline()); //腰围
        bundle.putString("hipline", bean.getHipline()); //臀围
        bundle.putStringArrayList("pics", picUrls); //对应技师的相册
        intent.putExtras(bundle);
        startActivityForResult(intent, 666);
    }

    /**
     * 刷新适配器数据
     */
    private void refreshData() {
        mAdapter.notifyDataSetChanged();
        mPftFrameLayout.refreshComplete();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //如果选择了对应的技师。返回的时候 移除对应的图片
        if (requestCode == 666 && resultCode == 888) {
            int position = data.getIntExtra("position", -1);
            LogUtils.d("onActivityResult。。。。。" + position);
            mDatas.remove(position);
            if (MyApplication.getBigPattern()) {
                mTvSize.setText("/ " + mDatas.size());
                mBigPagerAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemRemoved(position);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mTvCurrCount.setText((position + 1) + "");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onImgClick(int position) {
        gotoDetailActivity(position);
    }
}

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LogUtils.d("1111111.。。。onCreateView。。。。。。。。。");
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        LogUtils.d("1111111.。。。onCreate。。。。。。。。。");
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        LogUtils.d("1111111.。。。onActivityCreated。。。。。。。。。");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        LogUtils.d("1111111.。。。onStart。。。。。。。。。");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        LogUtils.d("1111111.。。。onResume。。。。。。。。。");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        LogUtils.d("1111111.。。。onStop。。。。。。。。。");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        LogUtils.d("1111111.。。。onDestroyView。。。。。。。。。");
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        LogUtils.d("1111111.。。。onDetach。。。。。。。。。");
//    }
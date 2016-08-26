package com.beabow.clickme.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beabow.clickme.MyApplication;
import com.beabow.clickme.intface.OnSuccessNoFalseListener;
import com.beabow.clickme.okhttp.OkHttpManager;
import com.beabow.clickme.tools.LogUtils;

/**
 * 创建者： lx
 * 创建时间：2016/5/31 10:02
 * 描述: TODO
 */
public abstract class BaseFragment extends Fragment {
    private LoadingPager mLoadingPager;
    public Activity mActivity;
    public OkHttpManager okManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = getActivity();
        okManager = MyApplication.getOkHttpManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
//        if(mLoadingPager!=null){
//            ViewGroup parent = (ViewGroup) mLoadingPager.getParent();
//            LogUtils.d("parent:" + parent+"---"+(container==parent));
//            if (parent != null) {
//                parent.removeView(mLoadingPager);
//            }
//        }

        LogUtils.d("BaseFragment。。。。" + (mLoadingPager == null)+":"+ getClass().getSimpleName() );
        mLoadingPager = new LoadingPager(mActivity, new OnSuccessNoFalseListener() {
            @Override
            public void onShow() {
                BaseFragment.this.show();
            }
        }) {
            @Override
            public LoadedResult initData(String id) {
                return BaseFragment.this.initData(id);
            }

            @Override
            public View initSuccessView() {
                return BaseFragment.this.initSuccessView(container);
            }
        };


//      LogUtils.d("BaseFragment加载界面是否为空===="+(mLoadingPager == null));
//        if (mLoadingPager == null) {
//            mLoadingPager = new LoadingPager(mActivity) {
//                @Override
//                public LoadedResult initData(String id) {
//                    return BaseFragment.this.initData(id);
//                }
//
//                @Override
//                public View initSuccessView() {
//                    return BaseFragment.this.initSuccessView(container);
//                }
//            };
//        } else {
//            ViewGroup parent = (ViewGroup) mLoadingPager.getParent();
//            LogUtils.d("parent:"+parent);
//            if(parent!=null){
//                parent.removeView(mLoadingPager);
//            }
//        }
        return mLoadingPager;
    }

    /**
     * 当前Fragment界面
     *
     * @return
     */
    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    /**
     * 加载服务器数据
     *
     * @param id 分类ID
     * @return
     */
    public abstract LoadingPager.LoadedResult initData(String id);

    /**
     * 加载成功界面
     */
    public abstract View initSuccessView(ViewGroup container);

    public void show() {
    }
}

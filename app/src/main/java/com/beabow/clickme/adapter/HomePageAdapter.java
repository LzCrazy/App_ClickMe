package com.beabow.clickme.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.beabow.clickme.base.BaseFragment;
import com.beabow.clickme.factory.FragmentFactory;
import com.beabow.clickme.tools.LogUtils;

import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/5/31 9:52
 * 描述: 列表ViewPager的适配器
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;

    public HomePageAdapter(FragmentManager fm, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = FragmentFactory.getFragment(position);
        LogUtils.d("HomePageAdapter。。。。" + fragment.getClass().getSimpleName() + "---"+(fragment == null));
        return fragment;
    }

    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

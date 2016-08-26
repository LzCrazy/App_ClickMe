package com.beabow.clickme.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.beabow.clickme.base.BaseFragment;
import com.beabow.clickme.factory.FragmentFactory;

import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/5/31 9:52
 * 描述: TODO
 */
public class HomePageAdapterCache extends FragmentPagerAdapter {
    private List<String> mTitles;
    public HomePageAdapterCache(FragmentManager fm, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment= FragmentFactory.getFragment(position);
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
}

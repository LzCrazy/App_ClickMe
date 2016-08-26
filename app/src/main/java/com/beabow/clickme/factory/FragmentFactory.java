package com.beabow.clickme.factory;

import android.util.SparseArray;

import com.beabow.clickme.base.BaseFragment;
import com.beabow.clickme.fragment.FiveFragment;
import com.beabow.clickme.fragment.FourFragment;
import com.beabow.clickme.fragment.OneFragment;
import com.beabow.clickme.fragment.SixFragment;
import com.beabow.clickme.fragment.ThreeFragment;
import com.beabow.clickme.fragment.TwoFragment;
import com.beabow.clickme.fragment.ZeroFragment;

/**
 * 创建者： lx
 * 创建时间：2016/5/31 10:07
 * 描述: TODO
 */
public class FragmentFactory {
    private static SparseArray<BaseFragment> mFragments = new SparseArray<>();
    public static final int FRAG_ZERO= 0;
    public static final int FRAG_ONE = 1;
    public static final int FRAG_TWO= 2;
    public static final int FRAG_THREE = 3;
    public static final int FRAG_FOUR = 4;
    public static final int FRAG_FIVE = 5;
    public static final int FRAG_SIX = 6;
    public static final int FRAG_SEVER = 7;

    public static BaseFragment getFragment(int position) {
//        if(mFragments==null){
//            mFragments = new SparseArray<>();
//        }
        BaseFragment fragment = mFragments.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case FRAG_ZERO:
                fragment = new ZeroFragment();//按摩
                break;
            case FRAG_ONE:
                fragment = new OneFragment();//按摩
                break;
            case FRAG_TWO:
                fragment = new TwoFragment();//足浴
                break;
            case FRAG_THREE:
                fragment = new ThreeFragment();//水疗
                break;
            case FRAG_FOUR:
                fragment = new FourFragment();//拔罐
                break;
            case FRAG_FIVE:
                fragment = new FiveFragment();//桑拿
                break;
            case FRAG_SIX:
                fragment = new SixFragment();//火罐
                break;
            case FRAG_SEVER:
                break;
            default:
                break;
        }
        // 保存对应的fragment
        mFragments.put(position, fragment);
        return fragment;
    }

    public static void clearFragment(){
        if(mFragments!=null){
            mFragments.clear();
            //mFragments=null;
        }
    }
}

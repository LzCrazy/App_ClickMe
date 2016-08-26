package com.beabow.clickme.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beabow.clickme.R;
import com.beabow.clickme.domain.PageListBean;
import com.beabow.clickme.tools.LogUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/6/10 11:50
 * 描述: 大图模式下的适配器
 */
public class BigPatternPageAdapter extends PagerAdapter {
    private List<PageListBean> mDatas;
    private Context context;

    public BigPatternPageAdapter(Context context,List<PageListBean> mDatas) {
        this.mDatas = mDatas;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_bigpattern, container, false);
        ImageView ivImg = (ImageView) view.findViewById(R.id.id_iv_img);
        TextView tvNum = (TextView) view.findViewById(R.id.id_tv_number);
        PageListBean bean = mDatas.get(position);
        Glide.with(context)
                .load(bean.getList_img())
                .centerCrop()
                .crossFade()
                .into(ivImg);
        tvNum.setText(bean.getJob_number());
        container.addView(view);
        MyListener listener = null;
        if (listener == null) {
            listener = new MyListener(position);
        }
        view.setOnClickListener(listener);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 这样可以notify刷新数据
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        if (mDatas.size() > 0) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }


    /**
     * 点击监听类
     */
    class MyListener implements View.OnClickListener {
        private int position;

        public MyListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onImgClick(position);
            }
        }
    }


    private OnImgClickListener listener;

    public interface OnImgClickListener {
        void onImgClick(int position);
    }

    public void setOnImgClickListener(OnImgClickListener listener) {
        this.listener = listener;
    }
}

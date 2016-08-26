package com.beabow.clickme.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.beabow.clickme.R;
import com.beabow.clickme.domain.PageListBean;
import com.beabow.clickme.tools.LogUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/6/3 14:09
 * 描述: 列表的适配器
 */
public class OneAdapter extends BaseRecyclerAdapter<PageListBean> {
    public OneAdapter(List<PageListBean> datas, int itemViewLayoutId) {
        super(datas, itemViewLayoutId);
    }

    @Override
    public void initData(BaseViewHolder holder, PageListBean data, int position) {
        ImageView img = holder.findView(R.id.id_iv_img);
        TextView tvNumber = holder.findView(R.id.id_tv_number);
        Glide.with(context)
                .load(data.getList_img())  //请求地址
                .placeholder(R.mipmap.iv_list_place)
                .centerCrop()  //居中裁剪
                .crossFade()  //交叉渐变
                .into(img);//显示的控件
        tvNumber.setText(data.getJob_number());
    }
}

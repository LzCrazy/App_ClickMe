package com.beabow.clickme.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beabow.clickme.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 包名：com.enjoydecorate.adapter
 * 创建者： lx
 * 创建时间：2016/4/21 10:31
 * 描述: TODO
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;
    public Context context;

    public List<T> mDatas;

    private int mItemViewLayoutId;

    public BaseRecyclerAdapter(List<T> datas, int itemViewLayoutId) {
        this.mDatas = datas;
        this.mItemViewLayoutId = itemViewLayoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = null;
        if (viewType == TYPE_HEADER) {
            view = mHeaderView;
            return new HeadViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(mItemViewLayoutId, parent, false);
            return new BaseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        /**
         * 头部图片在主界面加载了 ,这里如果是头View就直接return
         */
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (mHeaderView != null) {
                position--;
            }
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            if (!mDatas.isEmpty()) {
                T data = mDatas.get(position);
                holder.position = position;
                initData(holder, data, position);
            }
//            else {
//                initData(holder, null, position);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == TYPE_HEADER) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    /**
     * 当前位置是header的位置，那么该item占据2个单元格，正常情况下占据1个单元格
     * 如果你是下拉刷新的Recy。那么要手动调用这个方法
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 添加一个头布局
     */
    public void setHeadView(View headView) {
        this.mHeaderView = headView;
    }

    /**
     * 每个Item的 ViewHolder
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public View root;
        public SparseArray<View> viewSparseArray; //HashMap<Integer, E>的封装
        public int position;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.root = itemView;
            viewSparseArray = new SparseArray<>();
            if (mListener != null) {
                itemView.setOnClickListener(this);
            }
        }


        /**
         * 通过ViewHolder查找view然后存至SparseArray
         */
        protected <T extends View> T findView(int viewId) {
            View view = viewSparseArray.get(viewId);
            if (view == null) {
                view = root.findViewById(viewId);
                viewSparseArray.put(viewId, view);
            }
            return (T) root.findViewById(viewId);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, position);
        }
    }

    /**
     * 头部ViewHolder
     */
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 清空集合更新adapter
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 给Adapter添加数据
     */
    public void addAll(List<T> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 接口回调监听
     */
    private OnRecyclerViewClickListener mListener;

    public interface OnRecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 子类必须重写——初始化数据
     */
    public abstract void initData(BaseViewHolder holder, T data, int position);

    /**
     * 加载图片
     *
     * @param picUrl
     * @param img
     */
    public void loadImage(String picUrl, ImageView img) {
        Glide.with(context)
                .load(picUrl)  //请求地址
                .centerCrop()  //居中裁剪
                .crossFade()  //交叉渐变
                .into(img);//显示的控件
    }
}

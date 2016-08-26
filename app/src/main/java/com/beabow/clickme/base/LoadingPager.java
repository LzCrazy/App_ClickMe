package com.beabow.clickme.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.beabow.clickme.R;
import com.beabow.clickme.intface.OnSuccessNoFalseListener;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.UiUtils;

/**
 * 创建者： lx
 * 创建时间：2016/6/2 15:55
 * 描述: TODO
 */
public abstract class LoadingPager extends FrameLayout {

    public static final int STATE_NONE = -1;            // 默认情况
    public static final int STATE_LOADING = 0;            // 正在请求网络
    public static final int STATE_EMPTY = 1;            // 空状态
    public static final int STATE_SUCCESS = 2;            // 成功状态
    /**
     * @ -1 默认情况:  STATE_NONE
     * @ 0 正在请求网络: STATE_LOADING
     * @ 1 空状态:  STATE_EMPTY
     * @ 2 成功状态:  STATE_SUCCESS
     */
    public int mCurrState = STATE_NONE;
    private View mLoadingView;
    private View mEmptyView;
    private View mSuccessView;
    private Context context;
    private OnSuccessNoFalseListener listener;
    // private final OkHttpManager manager;
    private int testIndex = 0;

    public LoadingPager(Context context, OnSuccessNoFalseListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        initCommonView();
        // manager = MyApplication.getOkHttpManager();
    }

    /**
     * 默认加载页面
     */
    protected void initCommonView() {
        //① 加载页面
        //mLoadingView = View.inflate(context, R.layout.loading_pager, null);  //以前的旋转效果
        mLoadingView = View.inflate(context, R.layout.loading_dialog, null); //和加载网络一样的dialog
        this.addView(mLoadingView);
        // ②空页面
        mEmptyView = View.inflate(context, R.layout.empty_pager, null);
        this.addView(mEmptyView);

        refreshUI(STATE_NONE);// 1. LoadingPager初始化的时候
    }

    public void refreshUI(int mCurrState) {
        if (mCurrState == 666) {
            LogUtils.d("mCurrState。。。。。。。" + (mEmptyView == null));
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }
        // 控制loading视图显示隐藏
        mLoadingView.setVisibility((mCurrState == STATE_LOADING) || (mCurrState == STATE_NONE) ? View.VISIBLE : View.GONE);

        // 控制emptyView视图显示隐藏
        mEmptyView.setVisibility((mCurrState == STATE_EMPTY) ? View.VISIBLE : View.GONE);

        LogUtils.d("refreshUI。。。。。。" + (mSuccessView == null) + "  状态：" + mCurrState + " 刷新UI次数：" + testIndex);

        testIndex++;
        if (mSuccessView != null) {
            if (listener != null) {
                listener.onShow();
            }
        }
        if (mSuccessView == null && mCurrState == STATE_SUCCESS) {
            // 创建成功视图
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }

//        if (mSuccessView != null) {
//            // 控制success视图显示隐藏
//            mSuccessView.setVisibility((mCurrState == STATE_SUCCESS) ? View.VISIBLE : View.GONE);
//        }
    }

    /**
     * 加载数据
     */
    public void loadData(String id) {
        UiUtils.setRefreshCount(0);
        LogUtils.d("###开始加载数据。。当前状态。。。。" + mCurrState + "---刷新数量：：" + UiUtils.getRefreshCount());
        //如果不是显示成功界面 和 加载中界面
        if (mCurrState != STATE_SUCCESS && mCurrState != STATE_LOADING) {
            // 保证每次执行的时候一定是加载中视图,而不是上次的mCurState
            mCurrState = STATE_LOADING;
            refreshUI(mCurrState);// 2. 显示正在加载数据
            initData(id);
        } else if (mCurrState == STATE_LOADING || mCurrState == STATE_NONE) {
            mCurrState = STATE_LOADING;
            refreshUI(mCurrState);// 2. 显示正在加载数据
            initData(id);
        }
    }


    public abstract View initSuccessView();

    public abstract LoadedResult initData(String id);

    /**
     * 数据回调的枚举类 成功 或者 空
     */
    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), EMPTY(STATE_EMPTY);
        int state;

        public int getState() {
            return state;
        }

        private LoadedResult(int state) {
            this.state = state;
        }
    }
}

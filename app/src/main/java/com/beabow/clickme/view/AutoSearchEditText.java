package com.beabow.clickme.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;


/**
 * 自动搜索的自定义控件
 */
public class AutoSearchEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private boolean hasFocus;
    // 输入框内容改变后onTextChanged方法会调用多次，设置一个变量让其每次改变之后只调用一次
    private boolean isTextChange = false;

    public AutoSearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 控制onTextChanged每次改变只调用一次 ,
     * 不加hasFocus 第一次调用2次，后面才调用1次
     * 加hasFocus  第一次进来不调用
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus) {
            if (isTextChange) {
                isTextChange = false;
                return;
            }
            isTextChange = true;
            if (onAutoSearchListener != null) {
                onAutoSearchListener.onSearch(getText().toString());
            }
        }
    }


    private OnAutoSearchListener onAutoSearchListener;

    public interface OnAutoSearchListener {
        void onSearch(String s);
    }

    public void setOnAutoSearchListener(OnAutoSearchListener onAutoSearchListener) {
        this.onAutoSearchListener = onAutoSearchListener;
    }
}

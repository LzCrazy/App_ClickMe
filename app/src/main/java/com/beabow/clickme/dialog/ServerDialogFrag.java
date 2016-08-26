package com.beabow.clickme.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.beabow.clickme.Config;
import com.beabow.clickme.R;
import com.beabow.clickme.tools.LogUtils;
import com.beabow.clickme.tools.UiUtils;

/**
 * 创建者： lx
 * 创建时间：2016/6/6 10:16
 * 描述: 点击技师弹出服务的对话窗口
 */
public class ServerDialogFrag extends DialogFragment implements View.OnClickListener {

    private String mName = "", mJobNumber = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(Config.ARG_PARAM1);
            mJobNumber = getArguments().getString(Config.ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        int width = UiUtils.getScreenWidth().widthPixels;
        int space = UiUtils.dp2px(60);
        params.width = width - space;
        getDialog().getWindow().setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.server_dialog, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.id_tv_msg);
        LogUtils.d("mJobNumber:"+mJobNumber+"----"+mName);
        tvMsg.setText("选择" + mJobNumber + "号技师" + mName + "为您服务吗？");
        TextView tvCancel = (TextView) view.findViewById(R.id.id_tv_cancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.id_tv_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_cancel:
                getDialog().dismiss();
                break;
            case R.id.id_tv_confirm:
                if (listener != null) {
                    listener.onConfirmClick(2);
                }
                break;
        }
    }

    public static ServerDialogFrag newInstance(String name, String jobNumber) {
        ServerDialogFrag frag = new ServerDialogFrag();
        Bundle bundle = new Bundle();
        bundle.putString(Config.ARG_PARAM1, name);
        bundle.putString(Config.ARG_PARAM2, jobNumber);
        frag.setArguments(bundle);
        return frag;
    }

    /**
     * 接口回调
     */
    private OnClickListen listener;
    public interface OnClickListen {
        void onConfirmClick(int position);
    }

    public void setOnClickListen(OnClickListen listener) {
        this.listener = listener;
    }
}

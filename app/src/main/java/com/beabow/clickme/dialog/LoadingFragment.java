package com.beabow.clickme.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.beabow.clickme.R;


/**
 * 创建者： lx
 * 创建时间：2016/5/30 9:15
 * 描述: 网络加载提示框
 */
public class LoadingFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(),R.style.loadingDialog);
        dialog.setContentView(R.layout.loading_dialog);
        return dialog;
    }

}

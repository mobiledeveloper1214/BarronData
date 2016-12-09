package com.barrondata.android.views.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barrondata.android.AppConfig;
import com.barrondata.android.R;
import com.barrondata.android.api.GetDataTask;
import com.barrondata.android.api.HttpUrlManager;
import com.barrondata.android.utils.CommonUtils;

public class BaseFragment extends Fragment implements AppConfig, HttpUrlManager {
    public CommonUtils commonUtils = CommonUtils.getInstance();

    public boolean isLoadingBase;
    public ProgressDialog mProgressDialog;
    public GetDataTask mGetDataTask = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getContext(), R.style.ProgressDialogStyle);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void navWithoutFinish(Context mContext, Class mClass) {
        Intent intent = new Intent(mContext, mClass);
        navWithoutFinish(mContext, intent);
    }

    public void navWithoutFinish(Context mContext, Intent intent) {
        startActivity(intent);
    }

    public void setupUI(View view, Activity activity) {
        commonUtils.setupUI(view, activity);
    }
}

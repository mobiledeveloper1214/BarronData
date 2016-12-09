package com.barrondata.android.views.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.barrondata.android.AppConfig;
import com.barrondata.android.R;
import com.barrondata.android.api.GetDataTask;
import com.barrondata.android.api.HttpUrlManager;
import com.barrondata.android.utils.CommonUtils;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements AppConfig, HttpUrlManager {

    public CommonUtils commonUtils = CommonUtils.getInstance();
    public boolean isLoadingBase;
    public ProgressDialog mProgressDialog;
    public GetDataTask mGetDataTask = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "BaseActivity");

        mProgressDialog = new ProgressDialog(this, R.style.ProgressDialogStyle);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
    }

    public void navWithFinish(Activity mActivity, Class mClass) {
        Intent intent = new Intent(mActivity, mClass);
        navWithFinish(mActivity, intent);
    }

    public void navWithFinish(Activity mActivity, Intent intent) {
        startActivity(intent);
        mActivity.finish();
    }

    public void navWithoutFinish(Activity mActivity, Class mClass) {
        Intent intent = new Intent(mActivity, mClass);
        navWithoutFinish(intent);
    }

    public void navWithoutFinish(Intent intent) {
        startActivity(intent);
    }

    public void setupUI(View view, Activity activity) {
        commonUtils.setupUI(view, activity);
    }
}


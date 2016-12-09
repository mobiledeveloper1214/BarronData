package com.barrondata.android.views.user;

import android.os.Bundle;
import android.widget.EditText;

import com.barrondata.android.R;
import com.barrondata.android.views.base.BaseActivity;
import com.barrondata.android.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_user_id)  EditText etUserID;
    @BindView(R.id.et_password) EditText etPassword;

    private String strUserID, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initData();
        initUI();
    }

    private void initData() {
        strPassword = commonUtils.getStringFromSharedPreference(this, PASSWORD);

//        navWithFinish(this, MainActivity.class);
    }

    private void initUI() {
        setupUI(findViewById(R.id.parent), this);

        if (strPassword == null) {
            navWithFinish(this, SetPasswordActivity.class);
        }
    }

    @OnClick(R.id.btn_forgot_password)
    public void onForgotPassword() {
        navWithFinish(this, ForgotPasswordActivity.class);
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignIn() {
        strUserID   = etUserID.getText().toString().trim();
        strPassword = etPassword.getText().toString().trim();

        if (!strUserID.equals(commonUtils.getStringFromSharedPreference(this, USER_ID))) {
            commonUtils.showAlertDialog(this, "Your ID is incorrect.");
            return;
        }

        if (!strPassword.equals(commonUtils.getStringFromSharedPreference(this, PASSWORD))) {
            commonUtils.showAlertDialog(this, "Your Password is incorrect.");
            return;
        }

        navWithFinish(this, MainActivity.class);
    }
}

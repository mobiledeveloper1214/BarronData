package com.barrondata.android.views.user;

import android.os.Bundle;
import android.widget.EditText;

import com.barrondata.android.R;
import com.barrondata.android.views.main.MainActivity;
import com.barrondata.android.views.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends BaseActivity {
    @BindView(R.id.et_user_id)  EditText etUserID;
    @BindView(R.id.et_password) EditText etPassword;

    private String strUserLoginID, strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ButterKnife.bind(this);

        initData();
        initUI();
    }

    private void initData() {

    }

    private void initUI() {
        setupUI(findViewById(R.id.parent), this);
    }

    @OnClick(R.id.btn_set_password)
    public void onSetPassword() {
        strUserLoginID = etUserID.getText().toString().trim();
        strPassword    = etPassword.getText().toString().trim();

        if (strPassword.length() < 4) {
            commonUtils.showAlertDialog(this, "Password length must be between 4 and 8 characters.");
            return;
        }

        if (!strUserLoginID.equals("Demo")) {
            commonUtils.showAlertDialog(this, "Your ID is incorrect.");
            return;
        }

        commonUtils.setStringToSharedPreference(this, USER_ID, strUserLoginID);
        commonUtils.setStringToSharedPreference(this, PASSWORD, strPassword);

        navWithFinish(this, MainActivity.class);
    }
}

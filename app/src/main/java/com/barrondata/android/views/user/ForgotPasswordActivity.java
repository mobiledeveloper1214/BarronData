package com.barrondata.android.views.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.barrondata.android.R;
import com.barrondata.android.views.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.et_user_id)  EditText etUserID;

    private String strUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(this);

        initData();
        initUI();
    }

    @Override
    public void onBackPressed() {
        navWithFinish(this, LoginActivity.class);
    }

    private void initData() {

    }

    private void initUI() {
        setupUI(findViewById(R.id.parent), this);
    }

    @OnClick(R.id.btn_retrieve_password)
    public void onRetrievePassword() {
        strUserID = etUserID.getText().toString().trim();

        if (!strUserID.equals(commonUtils.getStringFromSharedPreference(this, USER_ID))) {
            commonUtils.showAlertDialog(this, "Your ID is incorrect.");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setTitle("Password");
        builder.setMessage("Your password is " + commonUtils.getStringFromSharedPreference(this, PASSWORD));
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

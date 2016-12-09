package com.barrondata.android.views.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.barrondata.android.R;
import com.barrondata.android.views.base.BaseActivity;

import org.json.JSONObject;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ListFragment.OnWorkOrderItemSelectedListener{

    private FragmentTransaction mTransaction;
    private ListFragment mListFragment;
    private DetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initData();
        initUI();
    }

    private void initData() {

    }

    private void initUI() {
        mListFragment    = new ListFragment();
        mDetailsFragment = new DetailsFragment();
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.list_fragment,   mListFragment);
        mTransaction.add(R.id.detail_fragment, mDetailsFragment);
        mTransaction.commit();
    }

    @Override
    public void onWorkOrderItemSelected(JSONObject workOrder) {
        Bundle bundle = new Bundle();
        bundle.putString("work_order", workOrder.toString());

        mDetailsFragment = new DetailsFragment();
        mDetailsFragment.setArguments(bundle);

        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.detail_fragment, mDetailsFragment);
        mTransaction.commit();
    }
}

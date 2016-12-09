package com.barrondata.android.views.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.barrondata.android.R;
import com.barrondata.android.views.base.BaseFragment;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends BaseFragment {
    @BindView(R.id.toolbar)          Toolbar    mToolbar;
    @BindView(R.id.detail_container) ScrollView mDetailContainer;
    @BindView(R.id.tv_work_order_title) TextView tvWorkOrderTitle;
    @BindView(R.id.tv_work_order_number) TextView tvWorkOrderNumber;

    private JSONObject workOrder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        initData(bundle);
        initUI();

        return view;
    }

    private void initData(Bundle bundle) {
        if (bundle != null) {
            String strWorkOrder = bundle.getString("work_order");
            if (strWorkOrder != null) {
                workOrder = commonUtils.createJSONObjectFromString(strWorkOrder);
            }
        }
    }

    private void initUI() {
        mDetailContainer.setVisibility(workOrder != null ? View.VISIBLE : View.INVISIBLE);

        if (workOrder != null) {
            showDetails();
        }
    }

    private void showDetails() {
        tvWorkOrderTitle.setText(commonUtils.getStringFromJSONObject(workOrder, WORK_ORDER_TITLE));
        tvWorkOrderNumber.setText(commonUtils.getStringFromJSONObject(workOrder, WORK_ORDER_NUMBER));
    }
}

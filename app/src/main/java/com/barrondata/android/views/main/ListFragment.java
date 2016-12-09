package com.barrondata.android.views.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.barrondata.android.R;
import com.barrondata.android.utils.RecyclerItemClickListener;
import com.barrondata.android.views.adapters.WorkOrderAdapter;
import com.barrondata.android.views.base.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends BaseFragment {
    @BindView(R.id.toolbar)       Toolbar      mToolbar;
    @BindView(R.id.rv_work_order) RecyclerView mRecyclerView;

    private static final int SORT_NONE        = 0;
    private static final int SORT_DUE_DATE    = 1;
    private static final int SORT_JOB_TYPE    = 2;
    private static final int SORT_PRIORITY    = 3;
    private static final int SORT_CRITICALITY = 4;

    private Context mContext;

    private WorkOrderAdapter mWorkOrderAdapter;
    private JSONArray workOrders, tempWorkOrders;
    private ArrayList<Boolean> itemSelected;

    private OnWorkOrderItemSelectedListener mCallback;

    public interface OnWorkOrderItemSelectedListener {
        void onWorkOrderItemSelected(JSONObject workOrder);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ButterKnife.bind(this, view);

        initData();
        initUI();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity = (Activity) context;
            try {
                mCallback = (OnWorkOrderItemSelectedListener) activity;
            } catch (ClassCastException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void initData() {
        workOrders = new JSONArray();
        itemSelected = new ArrayList<>();
        getWorkOrders();
    }

    private void initUI() {
        mToolbar.inflateMenu(R.menu.menu_list);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(!item.isChecked());

                switch (item.getItemId()) {
                    case R.id.menu_sort_none:
                        sort(SORT_NONE);
                        break;
                    case R.id.menu_sort_due_date:
                        sort(SORT_DUE_DATE);
                        break;
                    case R.id.menu_sort_job_type:
                        sort(SORT_JOB_TYPE);
                        break;
                    case R.id.menu_sort_priority:
                        sort(SORT_PRIORITY);
                        break;
                    case R.id.menu_sort_criticality:
                        sort(SORT_CRITICALITY);
                        break;
                }

                return false;
            }
        });

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < itemSelected.size(); i++) {
                    itemSelected.set(i, i == position);
                }

                mWorkOrderAdapter.notifyDataSetChanged();

                mCallback.onWorkOrderItemSelected(commonUtils.getJSONObjectFromJSONArray(workOrders, position));
            }
        }));
    }

    private void getWorkOrders() {
        JSONObject workOrder;
        try {
            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "LUB-070 - LUB, WIRE ROPE, LIFEBOAT, 2M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1601702");
            workOrder.put(DUE_DATE, "2016-08-06 00:00:00.000");
            workOrder.put(JOB_TYPE, 1);
            workOrder.put(PRIORITY, 1);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7601");
            workOrder.put(ASSET_NAME, "Davit/Launch Ramp- Free Fall lifeboat");
            workOrder.put("completed", true);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "INS-129 - INSPECT, DAVIT, LIFEBOAT, 1M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1602048");
            workOrder.put(DUE_DATE, "2016-08-06 00:00:00.000");
            workOrder.put(JOB_TYPE, 2);
            workOrder.put(PRIORITY, 2);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7601");
            workOrder.put(ASSET_NAME, "Davit/Launch Ramp- Free Fall lifeboat");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "INS-071 - INSPECT, WINCH & FALL, LIFEBOAT, 24M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1600473");
            workOrder.put(DUE_DATE, "2018-03-10 00:00:00.000");
            workOrder.put(JOB_TYPE, 2);
            workOrder.put(PRIORITY, 1);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7602");
            workOrder.put(ASSET_NAME, "Winch, FFLB Davit");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "INS-390 - INSPECT, WINCH, LIFEBOAT, 1W");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1602077");
            workOrder.put(DUE_DATE, "2016-07-15 00:00:00.000");
            workOrder.put(JOB_TYPE, 2);
            workOrder.put(PRIORITY, 2);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7602");
            workOrder.put(ASSET_NAME, "Winch, FFLB Davit");
            workOrder.put("completed", true);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "FCT-099 - FCT, DYNAMIC, WINCH BRAKE, 60M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPO1400088");
            workOrder.put(DUE_DATE, "2019-03-10 00:00:00.000");
            workOrder.put(JOB_TYPE, 3);
            workOrder.put(PRIORITY, 1);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7602");
            workOrder.put(ASSET_NAME, "Winch, FFLB Davit");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "INS-086 - INSPECT, WINCH, LIFEBOAT, 1M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1602049");
            workOrder.put(DUE_DATE, "2016-08-06 00:00:00.000");
            workOrder.put(JOB_TYPE, 3);
            workOrder.put(PRIORITY, 1);
            workOrder.put(CRITICALITY, "ER-06 - Lifeboats");
            workOrder.put(ASSET_TAG_NUMBER, "1042-L-7602");
            workOrder.put(ASSET_NAME, "Winch, FFLB Davit");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "FCT-157 - TEST, EMER. POWER SUPPLY, FFLB, 12M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPW1600470");
            workOrder.put(DUE_DATE, "2017-03-10 00:00:00.000");
            workOrder.put(JOB_TYPE, 1);
            workOrder.put(PRIORITY, 3);
            workOrder.put(CRITICALITY, null);
            workOrder.put(ASSET_TAG_NUMBER, "1042-EK-7302");
            workOrder.put(ASSET_NAME, "Control Panel-Free Fall Life Boat");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);

            workOrder = new JSONObject();
            workOrder.put(WORK_ORDER_TITLE, "CFT-016 - CFT, WELL SSSV VALVE INTEGRITY TEST, 12M");
            workOrder.put(WORK_ORDER_NUMBER, "WHPO1600050");
            workOrder.put(DUE_DATE, "2017-04-28 00:00:00.000");
            workOrder.put(JOB_TYPE, 1);
            workOrder.put(PRIORITY, 1);
            workOrder.put(CRITICALITY, null);
            workOrder.put(ASSET_TAG_NUMBER, "1000-XV-1902");
            workOrder.put(ASSET_NAME, "Valve, Production SSSV");
            workOrder.put("completed", false);
            workOrders = commonUtils.addJSONObjectToJSONArray(workOrders, workOrder);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        displayWorkOrders(workOrders);
    }

    private void sort(int type) {
        JSONArray result = null;
        switch (type) {
            case SORT_NONE:
                result = tempWorkOrders;
                break;
            case SORT_DUE_DATE:
                ArrayList<JSONObject> workOrderList = commonUtils.getArrayListFromJSONArray(tempWorkOrders);

                for (int i = 0; i < workOrderList.size() - 1; i++) {
                    for (int j = 0; j < workOrderList.size(); j++) {

                    }
                }

                break;
            case SORT_JOB_TYPE:
                break;
            case SORT_PRIORITY:
                break;
            case SORT_CRITICALITY:
                break;
        }

        displayWorkOrders(result);
    }

    private void displayWorkOrders(JSONArray workOrders) {
        if (workOrders == null) return;

        tempWorkOrders = workOrders;
        itemSelected.clear();

        for (int i = 0; i < tempWorkOrders.length(); i++) {
            itemSelected.add(false);
        }

        mWorkOrderAdapter = new WorkOrderAdapter(mContext, tempWorkOrders, itemSelected);
        mRecyclerView.setAdapter(mWorkOrderAdapter);
    }
}

package com.barrondata.android.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barrondata.android.AppConfig;
import com.barrondata.android.R;
import com.barrondata.android.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.ViewHolder> implements AppConfig{
    private Context mContext;
    private JSONArray workOrders;
    private ArrayList<Boolean> itemSelected;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_container)       LinearLayout mItemContainer;

        @BindView(R.id.tv_work_order_title)  TextView tvWorkOrderTitle;
        @BindView(R.id.tv_work_order_number) TextView tvWorkOrderNumber;
        @BindView(R.id.tv_job_type)          TextView tvJobType;
        @BindView(R.id.tv_due_date)          TextView tvDueDate;
        @BindView(R.id.tv_priority)          TextView tvPriority;
        @BindView(R.id.tv_asset_tag_number)  TextView tvAssetTagNumber;
        @BindView(R.id.tv_criticality)       TextView tvCriticality;
        @BindView(R.id.tv_asset_name)        TextView tvAssetName;
        @BindView(R.id.tv_job_number)        TextView tvJobNumber;

        @BindView(R.id.iv_comp_mark)         ImageView ivCompMark;
        @BindView(R.id.indicator)            RelativeLayout mIndicator;

        public ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            ButterKnife.bind(this, v);
        }
    }

    public WorkOrderAdapter(Context mContext, JSONArray workOrders, ArrayList<Boolean> itemSelected) {
        this.mContext     = mContext;
        this.workOrders   = workOrders;
        this.itemSelected = itemSelected;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CommonUtils commonUtils = CommonUtils.getInstance();
        JSONObject workOrder = commonUtils.getJSONObjectFromJSONArray(workOrders, position);

        String[] strJobTypes = {"PM", "Route", "Corrective"};
        int jobType = commonUtils.getIntFromJSONObject(workOrder, "job_type");

        holder.tvWorkOrderTitle.setText(commonUtils.getStringFromJSONObject(workOrder, WORK_ORDER_TITLE));
        holder.tvWorkOrderNumber.setText("Work Order #: " + commonUtils.getStringFromJSONObject(workOrder, WORK_ORDER_NUMBER));
        holder.tvJobType.setText("Job Type: " + strJobTypes[jobType - 1]);

        String strDueDate = commonUtils.getStringFromJSONObject(workOrder, "due_date");
        strDueDate = "Due Date: " + commonUtils.convertDateFormat(strDueDate);
        holder.tvDueDate.setText(strDueDate);

        holder.tvPriority.setText("Priority: " + commonUtils.getStringFromJSONObject(workOrder, "priority"));
        holder.tvAssetTagNumber.setText("Asset Tag #: " + commonUtils.getStringFromJSONObject(workOrder, "asset_tag_id"));
        holder.tvCriticality.setText("Criticality: " + commonUtils.getStringFromJSONObject(workOrder, "criticality"));
        holder.tvAssetName.setText("Asset Name: " + commonUtils.getStringFromJSONObject(workOrder, "asset_name"));
        holder.tvJobNumber.setText("Job Number: " + commonUtils.getStringFromJSONObject(workOrder, "job_index") + "/" + commonUtils.getStringFromJSONObject(workOrder, "job_count"));

        switch (jobType) {
            case 1:
                holder.mIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDarkBlue));
                break;
            case 2:
                holder.mIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorLightBlue));
                break;
            case 3:
                holder.mIndicator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorOrange));
                break;
        }

        holder.ivCompMark.setVisibility(commonUtils.getBooleanFromJSONObject(workOrder, "completed") ? View.VISIBLE : View.INVISIBLE);

        holder.mItemContainer.setBackgroundColor(ContextCompat.getColor(mContext, itemSelected.get(position) ? R.color.colorHighlightBlue: android.R.color.transparent));
    }

    @Override
    public int getItemCount() {
        return workOrders.length();
    }
}

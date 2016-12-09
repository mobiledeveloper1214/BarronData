package com.barrondata.android.api;

import org.json.JSONObject;

public interface OnTaskCompleted {
    void onDataReceived(JSONObject response);
}

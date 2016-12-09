package com.barrondata.android.api;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONObject;

public class GetDataTask extends AsyncTask<Void, Void, JSONObject> implements HttpUrlManager{
    private OnTaskCompleted listener;
    private String url;
    private ContentValues body;
    private int method;
    private Bitmap bitmap;

    public GetDataTask(String url, ContentValues body, int method, OnTaskCompleted listener) {
        this.url = url;
        this.body = body;
        this.method = method;
        this.bitmap = null;
        this.listener = listener;
    }

    public GetDataTask(String url, ContentValues body, int method, Bitmap bitmap, OnTaskCompleted listener) {
        this.url = url;
        this.body = body;
        this.method = method;
        this.bitmap = bitmap;
        this.listener = listener;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject result;

        result = Connect.getInstance().getResponse(url, body, method, bitmap);

        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        listener.onDataReceived(result);
    }
}

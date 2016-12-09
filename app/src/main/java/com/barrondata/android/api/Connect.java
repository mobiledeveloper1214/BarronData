package com.barrondata.android.api;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.util.Log;

import com.barrondata.android.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connect implements HttpUrlManager, AppConfig {
    private static Connect connect = null;

    public static Connect getInstance() {

        if (connect == null) {
            connect = new Connect();
        }
        return connect;
    }

    public JSONObject getResponse(String strUrl, ContentValues body, int method, Bitmap bitmap) {
        if (bitmap == null) {
            return getResponse(strUrl, body, method);
        } else {
            return postPicture(strUrl, bitmap);
        }
    }

    private JSONObject postPicture(String strUrl, Bitmap bitmap) {
        JSONObject result = null;
        String strResult;
        int status_code = 200;


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "image.jpg", RequestBody.create(MediaType.parse("image/jpg"), byteArray))
                .build();

        Request request = new Request.Builder()
                .url(strUrl)
                .post(requestBody)
                .addHeader(HEADER_ACCEPT, ACCEPT)
                .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
//                .addHeader(HEADER_AUTHORIZATION, AppController.authorization)
                .build();

        Log.d(TAG, "*********************** Request ***********************");
        if (request != null) {
            Log.d(TAG, "URL     : " + request.url().toString());
            Log.d(TAG, "Method  : " + request.method());
            Log.d(TAG, "Headers : ");
            Log.d(TAG, request.headers().toString());
        }

        try {
            Response response = client.newCall(request).execute();
            status_code = response.code();
            strResult = response.body().string();
            result = convertStringToJSONObject(strResult);

            if (status_code >= 200 && status_code < 300) {
                result = createResultJSONObject(result, 1);
            } else if (status_code >= 400) {
                result = createResultJSONObject(result, 2);
            } else {
                result = createResultJSONObject(result, 0);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        if (result == null) result = createResultJSONObject(null, 0);

        Log.d(TAG, " *******************   Response   *******************");
        Log.d(TAG, "Status Code : " + status_code);
        Log.d(TAG, "Response : " + result.toString());

        return result;
    }


    private JSONObject getResponse(String strUrl, ContentValues body, int method) {
        JSONObject result = null;
        String strResult;
        int status_code = 0;
        boolean isOauth = strUrl.equals(API_URL_OAUTH_ACCESS_TOKEN);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody;
        Request request;

        if (body != null) {
            requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), getQuery(body));
        } else {
            requestBody = RequestBody.create(MediaType.parse("text/plain"), "");
        }

        switch (method) {
            case POST_METHOD:
                request = new Request.Builder()
                        .url(strUrl)
                        .post(requestBody)
                        .addHeader(HEADER_ACCEPT, ACCEPT)
                        .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
//                        .addHeader(isOauth ? HEADER_X_API_KEY : HEADER_AUTHORIZATION, isOauth ? X_API_KEY : AppController.authorization)
                        .build();
                break;
            case GET_METHOD:
                request = new Request.Builder()
                        .url(strUrl)
                        .get()
                        .addHeader(HEADER_ACCEPT, ACCEPT)
                        .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
//                        .addHeader(HEADER_AUTHORIZATION, AppController.authorization)
                        .build();
                break;
            case PUT_METHOD:
                request = new Request.Builder()
                        .url(strUrl)
                        .put(requestBody)
                        .addHeader(HEADER_ACCEPT, ACCEPT)
                        .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
//                        .addHeader(HEADER_AUTHORIZATION, AppController.authorization)
                        .build();
                break;
            default:
                request = new Request.Builder()
                        .url(strUrl)
                        .delete(requestBody)
                        .addHeader(HEADER_ACCEPT, ACCEPT)
                        .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
//                        .addHeader(HEADER_AUTHORIZATION, AppController.authorization)
                        .build();
                break;
        }

        Log.d(TAG, "*********************** Request ***********************");
        if (request != null) {
            Log.d(TAG, "URL     : " + request.url().toString());
            Log.d(TAG, "Method  : " + request.method());
            Log.d(TAG, "Headers : ");
            Log.d(TAG, request.headers().toString());
            Log.d(TAG, "Body    : " + (body != null ? getQuery(body) : "NULL"));
        }

        try {
            Response response = client.newCall(request).execute();
            status_code = response.code();
            strResult = response.body().string();
            result = convertStringToJSONObject(strResult);

            if (status_code >= 200 && status_code < 300) {
                result = createResultJSONObject(result, 1);
            } else if (status_code >= 400) {
                result = createResultJSONObject(result, 2);
            } else {
                result = createResultJSONObject(result, 0);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        if (result == null) result = createResultJSONObject(null, 0);

        Log.d(TAG, " *******************   Response   *******************");
        Log.d(TAG, "Status Code : " + status_code);
        Log.d(TAG, "Response : " + result.toString());

        return result;
    }

    private JSONObject convertStringToJSONObject(String string){
        JSONObject result;

        try {
            result = new JSONObject(string);
        } catch (JSONException e1) {
            result = new JSONObject();
            try {
                result.put(RESULT, string);
            } catch (JSONException e2) {
                Log.e(TAG, e2.getMessage());
            }
        }

        return result;
    }

    private JSONObject createResultJSONObject(JSONObject result, int status) {
        if (result == null) result = new JSONObject();

        try {
            result.put(STATUS, status);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    private String getQuery(ContentValues body) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Set<Entry<String, Object>> set = body.valueSet();
        for (Object aSet : set) {
            if (first)
                first = false;
            else
                result.append("&");

            Entry entry = (Entry) aSet;
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            if (value != null) {
                try {
                    result.append(URLEncoder.encode(key, "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }

        return result.toString();
    }
}

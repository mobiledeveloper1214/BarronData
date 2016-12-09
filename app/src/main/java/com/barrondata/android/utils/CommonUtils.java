package com.barrondata.android.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.barrondata.android.AppConfig;
import com.barrondata.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils implements AppConfig {
    private static CommonUtils commonUtils = null;

    public static CommonUtils getInstance() {
        if (commonUtils == null) {
            commonUtils = new CommonUtils();
        }

        return commonUtils;
    }

    // SharedPreference
    private void setIntToSharedPreference(Context context, String key, int val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    private int getIntFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    public void setLongToSharedPreference(Context context, String key, long val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, val);
        editor.apply();
    }

    public long getLongFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, -1);
    }

    public void setStringToSharedPreference(Context context, String key, String val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public String getStringFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    private void setBooleanToSharedPreference(Context context, String key, boolean val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    private boolean getBooleanFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void setJSONObjectToSharedPreference(Context context, String key, JSONObject val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val == null ? null : val.toString());
        editor.apply();
    }

    private JSONObject getJSONObjectFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString(key, null);
        JSONObject result = null;

        if (s != null) {
            try {
                Object json = new JSONTokener(s).nextValue();
                if (json instanceof JSONObject) {
                    result = new JSONObject(s);
                }
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        return result;
    }

    public void setJSONArrayToSharedPreference(Context context, String key, JSONArray val) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val == null ? null : val.toString());
        editor.apply();
    }

    private JSONArray getJSONArrayFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        String s = sharedPreferences.getString(key, null);
        JSONArray result = null;
        if (s != null) {
            try {
                Object json = new JSONTokener(s).nextValue();

                if (json instanceof JSONArray) {
                    result = new JSONArray(s);
                }
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        return result;
    }

    public ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
            result.add(getJSONObjectFromJSONArray(jsonArray, i));

        return result;
    }

//    ***************************************** JSON *****************************************
//    JSONArray
    public JSONArray addJSONObjectToJSONArray(JSONArray jsonArray, JSONObject jsonObject) {
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }

        jsonArray.put(jsonObject);

        return jsonArray;
    }

    public JSONArray addJSONArrayToJSONArray(JSONArray jsonArray, JSONArray jArray) {
        if (jsonArray == null) {
            jsonArray = new JSONArray();
        }

        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                jsonArray.put(getJSONObjectFromJSONArray(jArray, i));
            }
        }

        return jsonArray;
    }

    public JSONArray removeJSONObjectToJSONArray(JSONArray jsonArray, int index) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            if (i != index) {
                try {
                    result.put(jsonArray.getJSONObject(i));
                } catch (JSONException e) {
                    Log.d(TAG, e.getMessage());
                }
            }
        }

        return result;
    }

    public boolean isExistInJSONArray(JSONArray jsonArray, String key, Object val) {
        boolean result = false;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = getJSONObjectFromJSONArray(jsonArray, i);

            if (jsonObject.has(key)) {
                Object object = getObjectFromJSONObject(jsonObject, key);

                if (object.toString().equals(val.toString())) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    public JSONObject getJSONObjectFromJSONArray(JSONArray jsonArray, int index) {
        JSONObject result = null;
        try {
            result = jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

//    JSONObject
    public JSONObject createJSONObjectFromString(String strJSONObject) {
        JSONObject result = null;
        try {
            result = new JSONObject(strJSONObject);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public JSONObject getJSONObjectFromJSONObject(JSONObject jsonObject, String key) {
        JSONObject result = null;

        if (jsonObject.has(key)) {
            try {
                String strVal = jsonObject.getString(key);
                Object json = new JSONTokener(strVal).nextValue();

                if (json instanceof JSONObject) {
                    result = new JSONObject(strVal);
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    public JSONArray getJSONArrayFromJSONObject(JSONObject jsonObject, String key) {
        JSONArray result = null;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getJSONArray(key);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    public String getStringFromJSONObject(JSONObject jsonObject, String key) {
        String result = null;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getString(key);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    public int getIntFromJSONObject(JSONObject jsonObject, String key) {
        int result = -1;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getInt(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public float getFloatFromJSONObject(JSONObject jsonObject, String key) {
        float result = -1;

        if (jsonObject.has(key)) {
            try {
                result = (float) jsonObject.getDouble(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public double getDoubleFromJSONObject(JSONObject jsonObject, String key) {
        double result = -1;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getDouble(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public long getLongFromJSONObject(JSONObject jsonObject, String key) {
        long result = -1;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getLong(key);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    public boolean getBooleanFromJSONObject(JSONObject jsonObject, String key) {
        boolean result = false;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.getBoolean(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private Object getObjectFromJSONObject(JSONObject jsonObject, String key) {
        Object result = null;

        if (jsonObject.has(key)) {
            try {
                result = jsonObject.get(key);
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
            }
        }

        return result;
    }

    public boolean isUserLoggedIn(Context context) {
        return true;
    }

    public void logOut(Context context) {
//        setJSONObjectToSharedPreference(context, USER,          null);
//        setJSONObjectToSharedPreference(context, AUTHORIZATION, null);
//        setJSONObjectToSharedPreference(context, BUSINESSES,    null);
//        setJSONObjectToSharedPreference(context, WALLET,        null);
    }

    public void setFilters(Context context) {
//        setBooleanToSharedPreference(context, PREFS_ENABLED,  AppController.prefs_enabled);
//        setBooleanToSharedPreference(context, RANGE_ENABLED,  AppController.range_enabled);
//        setBooleanToSharedPreference(context, PROMO_ENABLED,  AppController.promo_enabled);
//        setBooleanToSharedPreference(context, COMP_ENABLED,   AppController.comp_enabled);
//        setIntToSharedPreference(context,     RANGE_DISTANCE, AppController.range_distance);
    }

    //    ***************************************** AlertDialog with delay *****************************************
    public AlertDialog showAlertDialog(Context context, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        TextView alertMsg = new TextView(context);
        alertMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        alertMsg.setTextColor(ContextCompat.getColor(context, android.R.color.white));
        alertMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.font_size_normal));

        int pd = (int) context.getResources().getDimension(R.dimen.size_16);

        alertMsg.setPadding(pd, pd, pd, pd);
        alertMsg.setGravity(Gravity.CENTER_HORIZONTAL);

        alertMsg.setText(message);
        dialog.setView(alertMsg);

        final AlertDialog alert = dialog.create();
        alert.show();

        Handler handler  = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        };

        handler.postDelayed(runnable, ALERT_TIME);

        return alert;
    }

    public void setupUI(View view, final Activity activity) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public Boolean isValidEmail(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retrieving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public void fadeInView(View view, long duration) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public void fadeOutView(View view, long duration) {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    public void openUrlInBrowser(Context context, String url) {
        try {
            if(!url.contains("http://") && !url.contains("https://"))
                url = "http://" + url;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }
        catch(ActivityNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public String getQuery(ContentValues body){
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Set<Map.Entry<String, Object>> set = body.valueSet();
        for (Object aSet : set) {
            if (first)
                first = false;
            else
                result.append("&");

            Map.Entry entry = (Map.Entry) aSet;
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value != null) {
                result.append(key);
                result.append("=");
                result.append(value.toString());
            }
        }

        return result.toString();
    }

//    Format Date
    private SimpleDateFormat getInFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
    }

    private SimpleDateFormat getOutFormat(int type) {
        switch (type) {
            case 0:
                return new SimpleDateFormat("d-MMM-yyyy", Locale.US);
            default:
                return new SimpleDateFormat("MMM dd h:mm a", Locale.UK);
        }
    }

    public String getPostedDate(String strPostedDate) {
        String result;
        result = "Posted " + calcDifferenceDate(strPostedDate) + " ago";
        return result;
    }

    public String getLeftDate(String strEndDate) {
        String result;
        result = calcDifferenceDate(strEndDate) + " left";
        return result;
    }

    public String getDuration(String strStartDate, String strEndDate) {
        String result = "";
        SimpleDateFormat inFormat  = getInFormat();
        SimpleDateFormat outFormat = getOutFormat(0);

        try {
            Date startDate = inFormat.parse(strStartDate);
            Date endDate   = inFormat.parse(strEndDate);
            result = outFormat.format(startDate) + " - " + outFormat.format(endDate);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    private String calcDifferenceDate(String strPostedDate) {
        String result = "";
        SimpleDateFormat format = getInFormat();
        try {
            Date current = new Date();
            Date date = format.parse(strPostedDate);

            long different;

            if (current.after(date)) {
                different = current.getTime() - date.getTime();
            } else {
                different = date.getTime() - current.getTime() ;
            }

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;
            long weeksInMilli = daysInMilli * 7;
            long monthsInMilli = daysInMilli * 30;

            long elapsedMonths = different / monthsInMilli;
            different = different % monthsInMilli;

            long elapsedWeeks = different / weeksInMilli;
            different = different % weeksInMilli;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            if (elapsedMonths > 0) {
                result = elapsedMonths + " month" + (elapsedMonths > 1 ? "s" : "");
            } else if (elapsedWeeks > 0) {
                result = elapsedWeeks + " week" + (elapsedWeeks > 1 ? "s" : "");
            } else if (elapsedDays > 0) {
                result = elapsedDays + " day" + (elapsedDays > 1 ? "s" : "");
            } else if (elapsedHours > 0) {
                result = elapsedHours + " hour" + (elapsedHours > 1 ? "s" : "");
            } else if (elapsedMinutes > 0) {
                result = elapsedMinutes + " minute" + (elapsedMinutes > 1 ? "s" : "");
            } else if (elapsedSeconds > 0) {
                result = elapsedSeconds + " second" + (elapsedSeconds > 1 ? "s" : "");
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public String convertDateFormat(String strDate) {
        String result = "";
        SimpleDateFormat inFormat = getInFormat();
        SimpleDateFormat outFormat = getOutFormat(0);

        try {
            Date date = inFormat.parse(strDate);
            result = outFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    // View
    public void changeViewColor(View view, boolean enabled, Context context) {

//        if (view instanceof Button) {
//            Drawable[] drawables = ((Button) view).getCompoundDrawables();
//
//            for (Drawable drawable : drawables) {
//                if (drawable != null)
//                    drawable.setColorFilter(ContextCompat.getColor(context, enabled ? R.color.colorPrimary : R.color.colorGrayDark), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            ((Button) view).setTextColor(ContextCompat.getColor(context, enabled ? R.color.colorPrimary : R.color.colorGrayDark));
//        } else if (view instanceof TextView) {
//            Drawable[] drawables = ((TextView) view).getCompoundDrawables();
//
//            for (Drawable drawable : drawables) {
//                if (drawable != null)
//                    drawable.setColorFilter(ContextCompat.getColor(context, enabled ? R.color.colorPrimary : R.color.colorGrayDark), PorterDuff.Mode.SRC_ATOP);
//            }
//
//            ((TextView) view).setTextColor(ContextCompat.getColor(context, enabled ? R.color.colorPrimary : R.color.colorGrayDark));
//        }
    }

    public void changeActionButtonStateColor(Button button, boolean selected, Context context) {
//        Drawable[] drawables = button.getCompoundDrawables();
//
//        for (Drawable drawable : drawables) {
//            if (drawable != null)
//                drawable.setColorFilter(ContextCompat.getColor(context, selected ? android.R.color.white : R.color.colorGrayDark), PorterDuff.Mode.SRC_ATOP);
//        }
//
//        button.setTextColor(ContextCompat.getColor(context, selected ? android.R.color.white : R.color.colorGrayDark));
//        button.setBackgroundColor(ContextCompat.getColor(context, selected ? R.color.colorPrimary : R.color.colorGrayLight));
    }

    public boolean isConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}

package com.barrondata.android;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}

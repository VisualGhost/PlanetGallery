package com.ciklumtask;


import android.app.Application;

import com.ciklumtask.di.AppComponent;
import com.ciklumtask.di.DIHelper;
import com.ciklumtask.util.DebugLogger;
import com.facebook.stetho.Stetho;

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DIHelper.init();
        initLogging();
        initStetho();
    }

    public static AppComponent getAppComponent() {
        return DIHelper.getAppComponent();
    }

    private void initLogging() {
        if (BuildConfig.DEBUG) {
            DebugLogger.enableLogging();
        }
    }

    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}

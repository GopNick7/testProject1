package com.chi.nikita.testproject.ui;

import android.app.Application;

import com.chi.nikita.testproject.BuildConfig;
import com.facebook.stetho.Stetho;

public class ApplicationFinalProject extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}

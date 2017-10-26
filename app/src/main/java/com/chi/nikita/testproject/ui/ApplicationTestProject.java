package com.chi.nikita.testproject.ui;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.chi.nikita.testproject.BuildConfig;
import com.chi.nikita.testproject.data.db.DBManager;
import com.facebook.stetho.Stetho;

public class ApplicationTestProject extends Application {

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        if (!DBManager.isInit()) {
            DBManager.initDatabase(getApplicationContext(), handler);
            DBManager.getInstance().openDB();
        }
    }
}

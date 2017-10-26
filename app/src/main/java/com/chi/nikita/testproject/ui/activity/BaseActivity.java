package com.chi.nikita.testproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chi.nikita.testproject.data.db.DBManager;
import com.chi.nikita.testproject.ui.IPresenter;
import com.chi.nikita.testproject.ui.IView;

public abstract class BaseActivity<T extends IPresenter<V>, V extends IView> extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!DBManager.isInit()) {
            DBManager.initDatabase(getApplicationContext());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DBManager.getInstance().openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getInstance().closeDB();
    }
}

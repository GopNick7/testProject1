package com.chi.nikita.testproject.ui.activity.main;


import android.support.annotation.NonNull;

import com.chi.nikita.testproject.data.db.DBManager;
import com.chi.nikita.testproject.data.model.UserModel;

import java.util.List;

public class MainPresenterImpl implements MainPresenter<MainView> {

    private MainView view;

    @Override
    public void bindView(MainView view) {
        this.view = view;
    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void insertUser(@NonNull UserModel userModel) {
        DBManager.getInstance().insertUser(userModel);
    }

    @Override
       public void updateUser(@NonNull int id, @NonNull UserModel userModel) {
        DBManager.getInstance().updateUser(id, userModel);
    }

    @Override
    public void deleteUser(@NonNull int id) {
        DBManager.getInstance().deleteUser(id);
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserModel> userModelList = DBManager.getInstance().getAllUsers();
        return userModelList;
    }
}

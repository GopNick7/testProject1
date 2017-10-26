package com.chi.nikita.testproject.ui.activity.main;

import android.support.annotation.NonNull;

import com.chi.nikita.testproject.data.model.UserModel;
import com.chi.nikita.testproject.ui.IPresenter;
import com.chi.nikita.testproject.ui.IView;

public interface MainPresenter<T extends IView> extends IPresenter<T> {

    void insertUser(@NonNull UserModel userModel);

    void updateUser(int id, @NonNull UserModel userModel);

    void deleteUser(int id);

    void getAllUsers();

}

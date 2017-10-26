package com.chi.nikita.testproject.ui.activity.main;

import android.support.annotation.NonNull;

import com.chi.nikita.testproject.data.model.UserModel;
import com.chi.nikita.testproject.ui.IView;

public interface MainView extends IView {

    void onUserCRUD(@NonNull UserModel userModel);

    void onShowUsers(StringBuilder showUsers);
}

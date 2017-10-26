package com.chi.nikita.testproject.ui.activity.main;


import android.support.annotation.NonNull;

import com.chi.nikita.testproject.data.db.DBManager;
import com.chi.nikita.testproject.data.model.UserModel;

import java.util.List;

public class MainPresenterImpl implements MainPresenter<MainView> {

    private MainView view;
    private UserModel userModel = new UserModel();
    DBManager.ResultListener resultListener;

    @Override
    public void bindView(MainView view) {
        this.view = view;
        view.onUserCRUD(userModel);
    }

    @Override
    public void unbindView() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void insertUser(@NonNull UserModel userModel) {
        DBManager.getInstance().insertUserInDB(userModel);
    }

    @Override
    public void updateUser(@NonNull int id, @NonNull UserModel userModel) {
        DBManager.getInstance().updateUserInDB(id, userModel);
    }

    @Override
    public void deleteUser(@NonNull int id) {
        DBManager.getInstance().deleteUserInDB(id);
    }

    @Override
    public void getAllUsers() {
        final StringBuilder sb = new StringBuilder();
        resultListener = new DBManager.ResultListener() {
            @Override
            public void onSuccess(List<UserModel> userModelList) {
                for (UserModel users : userModelList) {
                    sb.append(users.getId());
                    sb.append(" - ");
                    sb.append(users.getFirstName());
                    sb.append(" - ");
                    sb.append(users.getLastName());
                    sb.append(" - ");
                    sb.append(users.getPhone());
                    sb.append("\n");
                }
                view.onShowUsers(sb);
            }
        };
        DBManager.getInstance().getAllUsersFromDB(resultListener);
    }
}

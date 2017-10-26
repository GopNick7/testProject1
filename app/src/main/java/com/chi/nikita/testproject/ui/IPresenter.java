package com.chi.nikita.testproject.ui;



public interface IPresenter<T extends IView> {
    void bindView(T view);

    void unbindView();

    void onDestroy();
}

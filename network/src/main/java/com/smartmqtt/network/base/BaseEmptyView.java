package com.smartmqtt.network.base;

public interface BaseEmptyView {

    void showEmpty();

    void showDataError(String msg);

    void showNetError(String msg);

    void showContent();

    void showLoading();

    void showLoadingCourse();

    void showToast(String msg);

    void showWaiting();
}

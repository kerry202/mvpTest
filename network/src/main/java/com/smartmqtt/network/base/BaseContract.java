package com.smartmqtt.network.base;


import com.smartmqtt.network.base.BaseEmptyView;


public interface BaseContract {
    interface BaseView extends BaseEmptyView {
    }

    interface BasePresenter<T extends BaseView> {
        void attachView(T view);

        void detachView();
    }
}

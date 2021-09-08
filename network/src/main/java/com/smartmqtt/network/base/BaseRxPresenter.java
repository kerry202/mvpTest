package com.smartmqtt.network.base;


import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class BaseRxPresenter<V extends BaseContract.BaseView> implements BaseContract.BasePresenter<V> {

    protected V mView;

    private CompositeDisposable subscription;

    public void addSubscribe(Disposable subscription) {
        if (this.subscription == null) {
            this.subscription = new CompositeDisposable();
        }
        this.subscription.add(subscription);
    }

    private void unSubscribe() {
        if (subscription != null) {
            subscription.clear();
        }
    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        unSubscribe();
        mView = null;
    }
}

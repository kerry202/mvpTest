package com.smartmqtt.network.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public abstract class BaseActivity<P extends BaseContract.BasePresenter<V>, V extends BaseContract.BaseView> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    @Nullable
    protected abstract P initPresenter();
}

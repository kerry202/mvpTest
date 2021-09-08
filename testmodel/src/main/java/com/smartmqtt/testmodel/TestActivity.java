package com.smartmqtt.testmodel;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.smartmqtt.basemodel.PathConfig;
import com.smartmqtt.network.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

/**
 * @author: kerry
 * date: On $ {DATE}
 */
@Route(path = PathConfig.TEXT_ACTIVITY)
public class TestActivity extends BaseActivity<TestPresenter, TestContract.View>
        implements TestContract.View {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    protected TestPresenter initPresenter() {
        return new TestPresenter();
    }

    @Override
    public void success(@NotNull String userBean) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showDataError(String msg) {

    }

    @Override
    public void showNetError(String msg) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingCourse() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showWaiting() {

    }
}

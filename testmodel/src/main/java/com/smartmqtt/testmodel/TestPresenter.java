package com.smartmqtt.testmodel;

import com.smartmqtt.network.http.RxUtils;
import com.smartmqtt.network.http.OkHttpUtils;
import com.smartmqtt.network.base.BaseRxPresenter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.disposables.Disposable;

public class TestPresenter extends BaseRxPresenter<TestContract.View> implements TestContract.Presenter {
    @Override
    public void send(@NotNull String universityId) {

        Disposable subscribe = OkHttpUtils.getRetrofit().create(TestService.class).send(TestParamsUtil.getParams()).compose(RxUtils.simpleTransformer())
                .subscribe(learningRecordResponse -> {
//                        mView.showLearningRecordList(learningRecordResponse);
                        }, null
//                        mView.showUniversityFail("")
                );

        addSubscribe(subscribe);
    }
}

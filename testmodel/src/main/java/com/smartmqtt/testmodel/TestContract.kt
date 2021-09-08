package com.smartmqtt.testmodel

import com.smartmqtt.network.base.BaseContract


class TestContract {

    interface View : BaseContract.BaseView {
        fun success(userBean: String)

    }

    interface Presenter : BaseContract.BasePresenter<View> {

        fun send(id: String)
    }
}
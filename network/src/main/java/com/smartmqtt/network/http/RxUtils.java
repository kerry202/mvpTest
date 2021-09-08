package com.smartmqtt.network.http;


import com.smartmqtt.network.base.BaseEmptyView;
import com.smartmqtt.network.base.BaseInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class RxUtils {
    private RxUtils() {
    }

    /**
     * 切换线程
     */
    public static <T> ObservableTransformer<T, T> supportSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 需要显示 load 时使用
     */
    public static <T> ObservableTransformer<T, T> showLoadingTransformer(BaseEmptyView view, boolean showLoading) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showLoading && view != null) {
                        view.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 需要显示 弹窗 load 时使用
     */
    public static <T> ObservableTransformer<T, T> showDialogLoadingTransformer(BaseEmptyView view, boolean showLoading) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showLoading && view != null) {
                        view.showWaiting();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 需要显示 waiting 时使用
     */
    public static <T> ObservableTransformer<T, T> showWaitingTransformer(BaseEmptyView view, boolean showLoading) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showLoading && view != null) {
                        view.showWaiting();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 判断 result code 是否正常
     */
    public static <T extends BaseInfo> ObservableTransformer<T, T> checkResponseResult() {
        return upstream -> upstream.map((Function<T, T>) t -> {
            if (t == null) {
                throw new ServerError("服务器数据为null", 400);
            }
            if (t.code == 200) {
                return t;
            } else {
                throw new ServerError("错误", 444);
            }
        });
    }

    /**
     * 通过 BaseBean 拿到　body  如果不需要那服务器返回code 做处理 可以通过它直接拿到code
     */
    public static <T> ObservableTransformer<BaseInfo<T>, T> transformerResult() {
        return upstream -> upstream.flatMap((Function<BaseInfo<T>, ObservableSource<T>>) resultBean -> {
            return createData(resultBean.data);
        });
    }

    public static <T> Observable<T> createData(final T t) {
        return Observable.create(emitter -> {
            try {
                if (t == null) {
                    try {
                        emitter.onNext((T) new String(""));
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(new ServerError("服务器数据为null", 500));
                    }
                } else {
                    emitter.onNext(t);
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }


    //对数据不进行处理 直接返回present 处理
    public static <T> ObservableTransformer<T, T> simpleNormalTransformer() {
        return upstream -> upstream
                .compose(RxUtils.<T>supportSchedulers())
                .retryWhen(new RetryWithDelay(1, 0));//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
    }

    //对数据进行处理 知道拿到data
    public static <T> ObservableTransformer<BaseInfo<T>, T> simpleTransformer() {
        return upstream -> upstream
                .compose(RxUtils.checkResponseResult())
                .compose(RxUtils.transformerResult())
                .compose(RxUtils.supportSchedulers())
                .retryWhen(new RetryWithDelay(1, 0));//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
    }

    /**
     * 错误处理
     */
    public abstract static class DealThrowable implements Consumer<Throwable> {
        BaseEmptyView view;

        public DealThrowable(BaseEmptyView view) {
            this.view = view;
        }

        @Override
        public void accept(Throwable throwable) throws Exception {
            if (isNetError(throwable)) {
                if (throwable instanceof HttpException) {
                    int code = ((HttpException) throwable).code();
                    if (code >= 400) {
                        Exception dataError = new Exception("服务器异常");
                        dataError(dataError);
                        return;
                    }
                }
                Exception netError = null;
                if (throwable instanceof SocketTimeoutException) {
                    netError = new Exception("请求超时，请重试");
                } else {
                    netError = new Exception("网络错误，请重试");
                }
                netError(netError);
            } else if (throwable instanceof ServerError) {
                dataError(throwable);
            } else {
                otherError(throwable);
            }

        }

        protected abstract void netError(Throwable throwable);

        protected abstract void dataError(Throwable throwable);

        protected abstract void otherError(Throwable throwable);
    }

    public static boolean isNetError(Throwable throwable) {
        return throwable instanceof HttpException || throwable instanceof SocketTimeoutException || throwable instanceof ConnectException
                || throwable instanceof UnknownHostException;
    }

}

package com.smartmqtt.network.http;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }


    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Throwable {
        return observable
                .flatMap(new Function<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> apply(Throwable throwable) throws Throwable {
                        if (++retryCount <= maxRetries) {
                            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
                            return Observable.timer(retryDelayMillis,
                                    TimeUnit.MILLISECONDS);
                        }
                        // Max retries hit. Just pass the error along.
                        return Observable.error(throwable);
                    }

                });
    }
}

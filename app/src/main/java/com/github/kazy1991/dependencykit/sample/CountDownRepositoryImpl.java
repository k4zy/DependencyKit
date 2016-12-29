package com.github.kazy1991.dependencykit.sample;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class CountDownRepositoryImpl implements CountDownRepository {

    @Inject
    public CountDownRepositoryImpl() {
    }

    @Override
    public Observable<Long> fetch() {
        return Observable.interval(1, TimeUnit.SECONDS).take(10);
    }
}

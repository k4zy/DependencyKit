package com.github.kazy1991.dependencykit.sample;


import rx.Observable;

public interface CountDownRepository {

    Observable<Long> fetch();

}

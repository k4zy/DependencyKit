package com.github.kazy1991.dependencykit.sample;


import com.github.kazy1991.dependencykit.Lazy;

public class CountDownRepositoryLazy implements Lazy<CountDownRepository> {

    @Override
    public CountDownRepository get() {
        return new CountDownRepositoryImpl();
    }
}

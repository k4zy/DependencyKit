package com.github.kazy1991.dependencykit.sample;

import android.app.Application;

import com.github.kazy1991.dependencykit.DependencyKit;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DependencyKit.configure(new SampleModule(this));
    }
}

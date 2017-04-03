package com.github.kazy1991.dependencykit.sample;

import android.app.Activity;
import android.app.Application;

import com.github.kazy1991.dependencykit.DependencyKit;

public class SampleApplication extends Application {

    private DependencyKit dependencyKit = new DependencyKit();

    public static DependencyKit dependencyKit(Activity activity) {
        return ((SampleApplication) activity.getApplication()).dependencyKit;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dependencyKit.configure(new SampleModule());
    }
}

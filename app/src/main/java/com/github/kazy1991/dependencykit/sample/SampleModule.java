package com.github.kazy1991.dependencykit.sample;


import android.content.Context;

import com.github.kazy1991.dependencykit.Module;

public class SampleModule extends Module {
    private Context context;

    public SampleModule(Context context) {
       this.context = context;
    }

    @Override
    protected void configure() {
        bind(CountDownRepository.class).to(CountDownRepositoryImpl.class);
    }
}

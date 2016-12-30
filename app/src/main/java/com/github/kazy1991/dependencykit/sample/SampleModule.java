package com.github.kazy1991.dependencykit.sample;


import com.github.kazy1991.dependencykit.Module;

public class SampleModule extends Module {

    @Override
    protected void configure() {
        bind(CountDownRepository.class).to(new CountDownRepositoryLazy());
    }
}

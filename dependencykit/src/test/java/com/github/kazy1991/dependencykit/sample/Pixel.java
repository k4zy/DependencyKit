package com.github.kazy1991.dependencykit.sample;

import javax.inject.Inject;

public class Pixel implements SmartPhone {

    @Inject
    public Pixel() {
    }

    @Override
    public String developer() {
        return "Google";
    }

    @Override
    public String os() {
        return "Android";
    }

    @Override
    public int storageSize() {
        return 64;
    }

    @Override
    public long weight() {
        return 300;
    }
}


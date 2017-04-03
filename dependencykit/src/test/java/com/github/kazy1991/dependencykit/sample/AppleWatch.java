package com.github.kazy1991.dependencykit.sample;

public class AppleWatch implements SmartWatch {
    @Override
    public String developer() {
        return "Apple";
    }

    @Override
    public boolean isWaterProof() {
        return true;
    }

    @Override
    public long weight() {
        return 62;
    }
}

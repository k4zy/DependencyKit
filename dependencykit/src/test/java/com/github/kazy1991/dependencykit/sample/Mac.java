package com.github.kazy1991.dependencykit.sample;


public class Mac implements Computer {
    @Override
    public String developer() {
        return "Apple";
    }

    @Override
    public String os() {
        return "macOS 10.12 Sierra";
    }

    @Override
    public int storageSize() {
        return 512;
    }

    @Override
    public long weight() {
        return 3000;
    }
}

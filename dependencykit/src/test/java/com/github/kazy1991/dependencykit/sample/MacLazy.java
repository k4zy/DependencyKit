package com.github.kazy1991.dependencykit.sample;


import com.github.kazy1991.dependencykit.Lazy;

public class MacLazy implements Lazy<Computer> {
    @Override
    public Computer get() {
        return new Mac();
    }
}

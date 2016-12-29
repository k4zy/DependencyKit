package com.github.kazy1991.dependencykit;


import java.util.HashMap;
import java.util.Map;

public abstract class Module {

    private Map<Class, Class> bindingMap = new HashMap<>();

    public Module() {
        configure();
    }

    public Map<Class, Class> getBindingMap() {
        return bindingMap;
    }

    abstract protected void configure();

    public Binding bind(Class from) {
        return new Binding(from);
    }

    public class Binding {

        private Class from;

        public Binding(Class from) {
            this.from = from;
        }

        public void to(Class impl) {
            bindingMap.put(from, impl);
        }

    }
}

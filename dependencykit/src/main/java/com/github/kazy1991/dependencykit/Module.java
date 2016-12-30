package com.github.kazy1991.dependencykit;


import java.util.HashMap;
import java.util.Map;

public abstract class Module {

    private Map<Class, Class> bindingMap = new HashMap<>();

    private Map<Class, Object> bindingInstanceMap = new HashMap<>();

    private Map<Class, Lazy> bindingLazyMap = new HashMap<>();

    public Module() {
        configure();
    }

    public Map<Class, Class> getBindingMap() {
        return bindingMap;
    }

    public Map<Class, Object> getBindingInstanceMap() {
        return bindingInstanceMap;
    }

    public Map<Class, Lazy> getBindingLazyMap() {
        return bindingLazyMap;
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

        public void to(Object instance) {
            bindingInstanceMap.put(from, instance);
        }

        public void to(Lazy lazy) {
            bindingLazyMap.put(from, lazy);
        }

    }
}

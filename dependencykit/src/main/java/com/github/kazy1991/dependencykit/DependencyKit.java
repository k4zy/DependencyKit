package com.github.kazy1991.dependencykit;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class DependencyKit {

    private Map<Class, Class> rootBindingMap = new HashMap<>();
    private Map<Class, Object> rootBindingInstanceMap = new HashMap<>();
    private Map<Class, Lazy> rootBindingLazyMap = new HashMap<>();

    public void configure(Module... modules) {
        for (Module module : modules) {
            module.configure();
            rootBindingMap.putAll(module.getBindingMap());
            rootBindingInstanceMap.putAll(module.getBindingInstanceMap());
            rootBindingLazyMap.putAll(module.getBindingLazyMap());
        }
    }

    public void inject(Object instance) {
        final Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (hasTargetAnnotation(field.getDeclaredAnnotations(), Inject.class)) {
                try {
                    field.setAccessible(true);
                    field.set(instance, get(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Could not inject field");
                }
            }
        }
    }

    private boolean hasTargetAnnotation(Annotation[] annotations, Class<? extends Annotation> target) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(target)) {
                return true;
            }
        }
        return false;
    }

    private Object get(Class<?> type) {
        if (rootBindingMap.containsKey(type)) {
            return getFromClass(type);
        } else if (rootBindingInstanceMap.containsKey(type)) {
            return getFromInstance(type);
        } else if (rootBindingLazyMap.containsKey(type)) {
            Object object = getFromLazy(type);
            rootBindingInstanceMap.put(type, object); // behave as Singleton but global polluted..
            return object;
        } else {
            throw new IllegalStateException("Could not find binding rule");
        }
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private Object getFromClass(Class<?> type) {
        Class<?> implType = rootBindingMap.get(type);
        for (Constructor constructor : implType.getDeclaredConstructors()) {
            if (hasTargetAnnotation(constructor.getDeclaredAnnotations(), Inject.class)) {
                try {
                    return constructor.newInstance(getParams(constructor));
                } catch (InstantiationException e) {
                    throw new IllegalStateException("Could not create instance");
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Could not create instance");
                } catch (InvocationTargetException e) {
                    throw new IllegalStateException("Could not create instance");
                }
            }
        }
        throw new IllegalStateException("Could not find @Inject constructor");
    }

    private Object getFromInstance(Class<?> type) {
        return rootBindingInstanceMap.get(type);
    }

    private Object getFromLazy(Class<?> type) {
        return rootBindingLazyMap.get(type).get();
    }

    private Object[] getParams(Constructor constructor) {
        Class[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = get(paramTypes[i]);
        }
        return params;
    }
}

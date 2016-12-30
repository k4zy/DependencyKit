package com.github.kazy1991.dependencykit;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class DependencyKit {

    private static Map<Class, Class> rootBindingMap = new HashMap<>();
    private static Map<Class, Object> rootBindingInstanceMap = new HashMap<>();

    public static void configure(Module... modules) {
        for (Module module : modules) {
            DependencyKit.rootBindingMap.putAll(module.getBindingMap());
            DependencyKit.rootBindingInstanceMap.putAll(module.getBindingInstanceMap());
        }
    }

    public static void inject(Object instance) {
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

    private static boolean hasTargetAnnotation(Annotation[] annotations, Class<? extends Annotation> target) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(target)) {
                return true;
            }
        }
        return false;
    }

    private static Object get(Class<?> type) {
        if (rootBindingMap.containsKey(type)) {
            return getFromClass(type);
        } else if (rootBindingInstanceMap.containsKey(type)) {
            return getFromInstance(type);
        } else {
            throw new IllegalStateException("Could not find binding rule");
        }
    }

    private static Object getFromInstance(Class<?> type) {
        return rootBindingInstanceMap.get(type);
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    private static Object getFromClass(Class<?> type) {
        Class<?> implType = rootBindingMap.get(type);
        for (Constructor constructor : implType.getDeclaredConstructors()) {
            if (hasTargetAnnotation(constructor.getDeclaredAnnotations(), Inject.class)) {
                Object[] args = new Object[0];
                try {
                    return constructor.newInstance(args);
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
}

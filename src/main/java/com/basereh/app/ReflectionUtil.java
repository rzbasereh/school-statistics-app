package com.basereh.app;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {
    public static <T> List<Class<? extends T>> getSubTypesOf(Class<T> tClass) {
        Reflections reflections = new Reflections("com.basereh.app");
        return reflections.getSubTypesOf(tClass).stream().toList();
    }

    public static <T> List<T> getInstancesOf(List<Class<? extends T>> classes)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<T> instances = new ArrayList<>();
        for (Class<? extends T> clazz : classes) {
            Constructor<? extends T> constructor = clazz.getConstructor();
             instances.add(constructor.newInstance());
        }
        return instances;
    }
}

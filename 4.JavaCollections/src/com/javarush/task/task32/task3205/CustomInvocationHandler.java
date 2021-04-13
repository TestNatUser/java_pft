package com.javarush.task.task32.task3205;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CustomInvocationHandler implements InvocationHandler {
    private SomeInterfaceWithMethods test;
    public CustomInvocationHandler(SomeInterfaceWithMethods test) {
        this.test=test;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+" in");
        Object t = method.invoke(test, args);
        System.out.println(method.getName()+" out");
        return t;
    }
}

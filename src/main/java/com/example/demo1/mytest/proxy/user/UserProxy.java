package com.example.demo1.mytest.proxy.user;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

public class UserProxy implements InvocationHandler {

    private UserService userService;

    public UserProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        if (Objects.nonNull(args)) {
            for (Object arg : args) {
                System.out.println(arg);
            }
        }
        Object res = method.invoke(userService, args);
        long end = System.currentTimeMillis();
        System.out.println("方法执行时间："  + (end - start) + " 毫秒");
        return res;
    }
}

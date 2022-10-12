package com.example.demo1.mytest.proxy.user;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

public class UserProxyHandler implements InvocationHandler {

    private Object proxiedBean;

    public UserProxyHandler(Object proxiedBean) {
        this.proxiedBean = proxiedBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        if (Objects.nonNull(args)) {
            for (Object arg : args) {
                System.out.println(arg);
            }
        }
        Object res = method.invoke(proxiedBean, args);
        long end = System.currentTimeMillis();
        System.out.println("方法执行时间："  + (end - start) + " 毫秒");
        return res;
    }
}

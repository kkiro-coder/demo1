package com.example.demo1.mytest.proxy.user;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

public class UserCglibProxy implements MethodInterceptor {

    private Object originBean;

    public UserCglibProxy() {
    }

    public UserCglibProxy(Object originBean) {
        this.originBean = originBean;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long start = System.currentTimeMillis();
        if (Objects.nonNull(objects)) {
            System.out.println("方法参数");
            for (Object arg : objects) {
                System.out.println(arg);
            }
        }
//        Object res = methodProxy.invokeSuper(o, objects); // CGLIB反射
        Object res = method.invoke(originBean, objects); // java反射
        long end = System.currentTimeMillis();
        System.out.println("方法执行时间："  + (end - start) + " 毫秒");
        return res;
    }
}

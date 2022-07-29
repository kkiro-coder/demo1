package com.example.demo1.mytest.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyProxy {


    private static final class MyProxyHandler implements InvocationHandler{

        private Object proxiedObject;

        public MyProxyHandler(Object proxiedObject) {
            this.proxiedObject = proxiedObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 业务逻辑
            return null;
        }
    }
}

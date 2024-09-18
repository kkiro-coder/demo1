package com.example.demo1.mytest.proxy;


import com.example.demo1.mytest.proxy.user.*;
import com.example.demo1.utils.ProxyUtil;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    public void testJdkProxy() {
        Object o = Proxy.newProxyInstance(
                UserService.class.getClassLoader(), UserService.class.getInterfaces(), new UserProxyHandler(new UserService()));
        ProxyUtil.writeByteCode2File("UserProxyMemory", UserService.class.getInterfaces(), "UserProxyMemory.class");
        UserAction userActionProxy = (UserAction) o;
        userActionProxy.login("kkiro", "8023");
        userActionProxy.changeNick("xudi");
        userActionProxy.changePwd("802311Xd");
        userActionProxy.logout();
    }

    @Test
    public void testCglibProxy() {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(UserSvc.class);
//        enhancer.setCallback(new UserCglibProxy(new UserSvc(new UserService())));
//        UserSvc proxy = (UserSvc) enhancer.create();
//        proxy.login("cglib_user", "802311");
//        proxy.changeNick("cglib_nick");
//        proxy.changePwd("8023cglib");
//        proxy.logout();
    }
}

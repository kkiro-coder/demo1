package com.example.demo1.mytest.proxy;


import com.example.demo1.mytest.proxy.user.UserAction;
import com.example.demo1.mytest.proxy.user.UserProxy;
import com.example.demo1.mytest.proxy.user.UserService;
import com.example.demo1.utils.ProxyUtil;

import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        Object o = Proxy.newProxyInstance(
                UserService.class.getClassLoader(), UserService.class.getInterfaces(), new UserProxy(new UserService()));
        ProxyUtil.writeByteCode2File("UserProxyMemory", UserService.class.getInterfaces(), "UserProxyMemory.class");
        UserAction userActionProxy = (UserAction) o;
        userActionProxy.login("kkiro", "8023");
        userActionProxy.changeNick("xudi");
        userActionProxy.changePwd("802311Xd");
        userActionProxy.logout();
    }
}

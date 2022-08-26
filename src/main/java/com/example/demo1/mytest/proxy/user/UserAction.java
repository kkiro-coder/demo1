package com.example.demo1.mytest.proxy.user;

public interface UserAction {

    void login(String username, String password);
    void logout();
    void changePwd(String password);
    void changeNick(String nick);
}

package com.example.demo1.mytest.proxy.user;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class UserSvc {

    private UserService userService;

    public UserSvc() {
    }

    public UserSvc(UserService userService) {
        this.userService = userService;
    }

    private static final String PATH = System.getProperty("user.dir") + File.separator;

    public void login(String username, String password) {
        userService.login(username, password);
    }

    public void logout() {
        userService.logout();
    }

    public void changePwd(String password) {
        userService.changePwd(password);
    }

    public void changeNick(String nick) {
        userService.changeNick(nick);
    }

}

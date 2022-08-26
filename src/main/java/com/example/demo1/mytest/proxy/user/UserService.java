package com.example.demo1.mytest.proxy.user;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class UserService implements UserAction{
    private static final String PATH = System.getProperty("user.dir") + File.separator;

    @Override
    public void login(String username, String password) {
        if (doSomeThingIO("login.txt")) {
            try (BufferedOutputStream bufOut = new BufferedOutputStream(Files.newOutputStream(Paths.get(PATH + "login.txt")))) {
                String out = String.format("user login!, user json = {\"username\":%s, \"password\":%s}", username, password);
                bufOut.write(out.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void logout() {
        if (doSomeThingIO("logout.txt")) {
            try (BufferedOutputStream bufOut = new BufferedOutputStream(Files.newOutputStream(Paths.get(PATH + "logout.txt")))) {
                String out = "user logout!";
                bufOut.write(out.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void changePwd(String password) {
        if (doSomeThingIO("changePwd.txt")) {
            try (BufferedOutputStream bufOut = new BufferedOutputStream(Files.newOutputStream(Paths.get(PATH + "changePwd.txt")))) {
                String out = "password change, new password is " + password;
                bufOut.write(out.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void changeNick(String nick) {
        if (doSomeThingIO("changeNick.txt")) {
            try (BufferedOutputStream bufOut = new BufferedOutputStream(Files.newOutputStream(Paths.get(PATH + "changeNick.txt")))) {
                String out = "nick name change, new nick name is " + nick;
                bufOut.write(out.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    private boolean doSomeThingIO(String fileName) {
        File file = new File(PATH + fileName);
        boolean created = false;
        try {
            if (file.exists()) {
                if (!file.delete()) {
                    log.info("删除文件{}失败", fileName);
                    return false;
                }
            }
            created = file.createNewFile();
        } catch (IOException e) {
            log.error("create file error", e);
        }
        return created;
    }
}

package com.example.demo1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotiationTest {

    @Value("${tcp.port}")
    private int port = 8000;

    @Value("${tcp.ip}")
    private String ip = "10.134.166.221";

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

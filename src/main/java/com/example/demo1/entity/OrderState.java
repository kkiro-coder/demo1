package com.example.demo1.entity;

public enum OrderState {
    INIT("init"),
    MAKING("making"),
    FINISH("finish"),
    CANCEL("cancel");

    private String state;

    OrderState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

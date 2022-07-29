package com.example.demo1.mytest.ps;

public class SonOne extends Parent {

    private String work;

    public SonOne() {
    }

    public SonOne(String work) {
        this.work = work;
    }

    @Override
    protected String doSomething(String str) {
        return str + work;
    }

    public void work() {
        passByDad();
    }
}

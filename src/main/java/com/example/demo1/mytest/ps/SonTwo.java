package com.example.demo1.mytest.ps;

public class SonTwo extends Parent{

    private Integer res;

    public SonTwo() {
    }

    public SonTwo(Integer res) {
        this.res = res;
    }

    public void cal() {
        passByDad();
    }

    @Override
    protected String doSomething(String str) {
        return  str + "the res is equal " + res;
    }
}

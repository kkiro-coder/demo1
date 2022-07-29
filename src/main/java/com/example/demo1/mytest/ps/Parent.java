package com.example.demo1.mytest.ps;

public abstract class Parent {

    protected void passByDad() {
        System.out.println(doSomething("agree and pass "));
    }

    protected abstract String doSomething(String str);
}

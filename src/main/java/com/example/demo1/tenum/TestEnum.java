package com.example.demo1.tenum;

public enum  TestEnum {

    A1("A1", 10),
    A3("A3", 7),
    A2("A2", 9);
   ;

    private String name;

    private Integer num;

    TestEnum(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "TestEnum{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}

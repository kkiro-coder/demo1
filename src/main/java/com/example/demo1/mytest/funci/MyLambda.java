package com.example.demo1.mytest.funci;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MyLambda {

    public static volatile int count = 0;
    private class Vo{


        public Vo() {
        }

        public Vo(String field1, List<Integer> fields2, Boolean fields3) {
            this.field1 = field1;
            this.fields2 = fields2;
            this.fields3 = fields3;
        }

        public Vo(Integer fields4) {
            this.fields4 = fields4;
        }

        private String field1;

        private List<Integer> fields2;

        private Boolean fields3;

        private Integer fields4;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public List<Integer> getFields2() {
            return fields2;
        }

        public void setFields2(List<Integer> fields2) {
            this.fields2 = fields2;
        }

        public Boolean getFields3() {
            return fields3;
        }

        public void setFields3(Boolean fields3) {
            this.fields3 = fields3;
        }

        public Integer getFields4() {
            return fields4;
        }

        public void setFields4(Integer fields4) {
            this.fields4 = fields4;
        }

        public Vo(String field1, List<Integer> fields2, Boolean fields3, Integer fields4) {
            this.field1 = field1;
            this.fields2 = fields2;
            this.fields3 = fields3;
            this.fields4 = fields4;
        }
    }

    public void f1() {
        List<Vo> voList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            voList.add(new Vo(i));
        }
        System.out.println(voList);
    }

    public void f2() {
        List<Vo> collect = IntStream.rangeClosed(1, 10).mapToObj(Vo::new).collect(Collectors.toList());
        System.out.println(collect);
    }
}

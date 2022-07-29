package com.example.demo1.jpatest.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class TestData {
    private String str;
    private final Map<String, String> map = new LinkedHashMap<>();
}

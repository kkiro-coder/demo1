package com.example.demo1.listener;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class MultiHeaderModel {

    @ExcelProperty(index = 0)
    private String adc;
    @ExcelProperty(value = "T0")
    private String t0;
    @ExcelProperty(value = "T1")
    private String t1;
    @ExcelProperty(value = "T2")
    private String t2;

    @Override
    public String toString() {
        return "MultiHeaderModel{" +
                "adc='" + adc + '\'' +
                ", t0='" + t0 + '\'' +
                ", t1='" + t1 + '\'' +
                ", t2='" + t2 + '\'' +
                '}';
    }
}

package com.example.demo1.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CustomerExcelModel {
    @ExcelProperty(value = "username")
    private String userName;
    @ExcelProperty(value = "couplename")
    private String coupleName;
}

package com.example.demo1.entity;

import lombok.Data;

import java.util.List;

@Data
public class TreeNodeVo {

    private Long id;
    private Long parentId;
    private Integer level;
    private String name;

    private List<TreeNodeVo> childrenList;
    private boolean leaf;

}

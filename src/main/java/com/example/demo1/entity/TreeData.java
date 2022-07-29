package com.example.demo1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_TREE_DATA")
@Data
public class TreeData {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "level")
    private Integer level;

    @Column(name = "root_id")
    private Long rootId;
}

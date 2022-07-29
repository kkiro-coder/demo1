package com.example.demo1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "test_uc", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_TEST_UC_COUPLE", columnNames="couple")})
@Data
public class TestUniqueColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "username")
    private String username;

    @Column(name = "couple")
    private String couplename;

    @Column(name = "age")
    private Integer age;
}

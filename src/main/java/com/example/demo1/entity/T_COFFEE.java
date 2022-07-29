package com.example.demo1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_COFFEE")
@Data
public class T_COFFEE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "namezh")
    private String namezh;

    @Column(name = "nameen")
    private String nameen;

//    @OneToOne
//    @Column(name = "price")
//    private double price;
}

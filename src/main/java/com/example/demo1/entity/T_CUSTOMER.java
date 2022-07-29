package com.example.demo1.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames="couple")})
public class T_CUSTOMER {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "username")
    private String username;

    @Column(name = "couple")
    private String couplename;

    public T_CUSTOMER() {
    }

    public String getCouplename() {
        return couplename;
    }

    public void setCouplename(String couplename) {
        this.couplename = couplename;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

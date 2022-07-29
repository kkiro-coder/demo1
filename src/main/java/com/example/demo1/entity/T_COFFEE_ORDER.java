package com.example.demo1.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "T_COFFEE_ORDER")
public class T_COFFEE_ORDER {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "createtime")
    private Calendar createtime;

    @Column(name = "updatetime")
    private Calendar updatetime;

    @Column(name = "coffeenamezh", columnDefinition = "varchar(255)")
    private String coffeenamezh;

    @Column(name = "coffeenameen", columnDefinition = "varchar(255)")
    private String coffeenameen;

    @Column(name = "totalsum")
    private double totalsum;

    @Column(name = "coffeeid")
    private String coffeeid;

    @Column(name = "customid")
    private long customid;

    @Column(name = "customname", columnDefinition = "varchar(255)")
    private String customname;

    @Column(name = "remark", columnDefinition = "TEXT")
    private String remark;

    public T_COFFEE_ORDER() {
    }

    public String getCustomname() {
        return customname;
    }

    public void setCustomname(String customname) {
        this.customname = customname;
    }

    public long getCustomid() {
        return customid;
    }

    public void setCustomid(long customid) {
        this.customid = customid;
    }

    public String getCoffeeid() {
        return coffeeid;
    }

    public void setCoffeeid(String coffeeid) {
        this.coffeeid = coffeeid;
    }

    public double getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(double totalsum) {
        this.totalsum = totalsum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Calendar createtime) {
        this.createtime = createtime;
    }

    public Calendar getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Calendar updatetime) {
        this.updatetime = updatetime;
    }

    public String getCoffeenamezh() {
        return coffeenamezh;
    }

    public void setCoffeenamezh(String coffeenamezh) {
        this.coffeenamezh = coffeenamezh;
    }

    public String getCoffeenameen() {
        return coffeenameen;
    }

    public void setCoffeenameen(String coffeenameen) {
        this.coffeenameen = coffeenameen;
    }
}

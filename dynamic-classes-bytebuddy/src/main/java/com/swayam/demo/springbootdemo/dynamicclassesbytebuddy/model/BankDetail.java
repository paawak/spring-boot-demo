package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bank_details")
@Data
public class BankDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int age;

    @Column
    private String job;

    @Column
    private String marital;

    @Column
    private String education;

    @Column
    private String defaulted;

    @Column
    private BigDecimal balance;

    @Column
    private String housing;

    @Column
    private String loan;

    @Column
    private String contact;

    @Column
    private int day;

    @Column
    private String month;

    @Column
    private int duration;

    @Column
    private int campaign;

    @Column
    private int pdays;

    @Column
    private int previous;

    @Column
    private String poutcome;

    @Column
    private String y;

}

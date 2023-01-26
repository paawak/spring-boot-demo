package com.swayam.demo.springbootdemo.ecs.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "BANK_DETAIL")
@Entity
@Data
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private int age;
    private String job;
    private String marital;
    private String education;
    private String defaulted;
    private BigDecimal balance;
    private String housing;
    private String loan;
    private String contact;
    private int day;
    private String month;
    private int duration;
    private int campaign;
    private int pdays;
    private int previous;
    private String poutcome;
    private String y;

}

package com.swayam.demo.springbootdemo.ecs.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "BANK_DETAILS")
@Entity
@Data
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
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
    private int recordDay;
    @Column
    private String recordMonth;
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

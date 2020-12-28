package com.swayam.demo.springboot.googleauth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column
    private String country;

}

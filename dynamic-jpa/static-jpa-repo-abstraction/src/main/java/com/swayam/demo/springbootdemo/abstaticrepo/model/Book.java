package com.swayam.demo.springbootdemo.abstaticrepo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends BookTemplateImpl {

}

package com.swayam.demo.springboot.googleauth.model.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "page_image")
@Data
public class PageImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private String name;

    @Column(name = "page_number")
    private int pageNumber;

    @Column
    private boolean ignored;

    @Column(name = "correction_completed")
    private boolean correctionCompleted;

}

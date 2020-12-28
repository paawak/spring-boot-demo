package com.swayam.demo.springboot.googleauth.model.old;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import lombok.Data;

@MappedSuperclass
@Data
public class CorrectedWordEntityTemplate implements CorrectedWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserDetails user;

    @Column(name = "ocr_word_id")
    private long ocrWordId;

    @Column(name = "corrected_text")
    private String correctedText;

    @Column
    private boolean ignored;

}

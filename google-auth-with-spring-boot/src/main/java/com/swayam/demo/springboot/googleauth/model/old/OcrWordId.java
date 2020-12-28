package com.swayam.demo.springboot.googleauth.model.old;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OcrWordId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "book_id")
    private long bookId;

    @Column(name = "page_image_id")
    private long pageImageId;

    @Column(name = "word_sequence_id")
    private int wordSequenceId;

}

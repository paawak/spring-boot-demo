package com.swayam.demo.springboot.googleauth.rest.train.dto;

import java.util.Collections;
import java.util.List;

import com.swayam.demo.springboot.googleauth.model.old.CorrectedWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWord;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;

import lombok.Data;

@Data
public class OcrWordOutputDto implements OcrWord, CorrectedWord {

    private long id;

    private OcrWordId ocrWordId;

    private String rawText;

    private int x1;

    private int y1;

    private int x2;

    private int y2;

    private float confidence;

    private Integer lineNumber;

    private String correctedText;

    private boolean ignored;

    @Override
    public List<CorrectedWord> getCorrectedWords() {
	return Collections.emptyList();
    }

    @Override
    public UserDetails getUser() {
	return null;
    }

}

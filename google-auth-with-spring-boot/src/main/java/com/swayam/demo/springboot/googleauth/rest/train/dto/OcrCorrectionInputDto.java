package com.swayam.demo.springboot.googleauth.rest.train.dto;

import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;

import lombok.Data;

@Data
public class OcrCorrectionInputDto {

    private OcrWordId ocrWordId;

    private String correctedText;

}

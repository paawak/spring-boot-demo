package com.swayam.ocr.porua.tesseract.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.swayam.demo.springboot.googleauth.model.old.OcrWordEntityTemplate;

@Entity
@Table(name = "ocr_word")
public class OcrWordEntity extends OcrWordEntityTemplate {

}

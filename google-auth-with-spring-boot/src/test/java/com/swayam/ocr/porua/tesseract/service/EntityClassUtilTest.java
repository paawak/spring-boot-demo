package com.swayam.ocr.porua.tesseract.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.swayam.demo.springboot.googleauth.service.EntityClassUtil;
import com.swayam.demo.springboot.googleauth.service.EntityClassUtil.EntityClassDetails;

class EntityClassUtilTest {

    @Test
    void testEntityClassDetails() {
	// given
	EntityClassUtil testClass = new EntityClassUtil();

	// when
	EntityClassDetails result = testClass.getEntityClassDetails("my_abc_book_espanol");

	// then
	assertEquals("com.swayam.demo.springboot.googleauth.model.old.dynamic.MyAbcBookEspanolOcrWordEntity", result.getOcrWordEntity());
	assertEquals("com.swayam.demo.springboot.googleauth.model.old.dynamic.MyAbcBookEspanolCorrectedWordEntity", result.getCorrectedWordEntity());
	assertEquals("com.swayam.demo.springboot.googleauth.repo.dynamic.MyAbcBookEspanolOcrWordRepository", result.getOcrWordEntityRepository());
	assertEquals("com.swayam.demo.springboot.googleauth.repo.dynamic.MyAbcBookEspanolCorrectedWordRepository", result.getCorrectedWordEntityRepository());
    }

}

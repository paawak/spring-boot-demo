package com.swayam.ocr.porua.tesseract.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.swayam.demo.springboot.googleauth.model.old.OcrWordId;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.rest.train.dto.OcrWordOutputDto;
import com.swayam.demo.springboot.googleauth.service.OcrWordServiceImpl;
import com.swayam.ocr.porua.tesseract.model.OcrWordEntity;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class OcrWordServiceImplIntegrationTest {

    private static final String SELECT_FROM_OCR_WORD =
	    "SELECT book_id, page_image_id, word_sequence_id, raw_text, corrected_text, x1, y1, x2, y2, confidence, ignored FROM ocr_word ORDER BY word_sequence_id ASC";

    @Autowired
    private OcrWordServiceImpl testClass;

    @Autowired
    private JdbcOperations jdbcTemplate;

    @BeforeEach
    void setupBookAndRawImage() {
	jdbcTemplate.execute("TRUNCATE TABLE book");
	jdbcTemplate.update("INSERT INTO book (id, name, language) VALUES (1, 'TEST BOOK 1', 'ben')");
	jdbcTemplate.update("INSERT INTO book (id, name, language) VALUES (2, 'TEST BOOK 2', 'eng')");
	jdbcTemplate.update("INSERT INTO page_image (id, book_id, name, page_number) VALUES (1, 1, 'TEST IMAGE 1.jpg', 1)");
	jdbcTemplate.update("INSERT INTO page_image (id, book_id, name, page_number) VALUES (2, 1, 'TEST IMAGE 2.jpg', 2)");
    }

    @Test
    void testAddOcrWord() {
	// given
	OcrWordEntity ocrWord = getOcrWord(1, 1, 11, 22, 33, 44, 55.55f, "ABC123", 1);

	List<OcrWordEntity> expected = Arrays.asList(ocrWord);

	// when
	testClass.addOcrWord(ocrWord);

	// then
	List<OcrWordEntity> results = jdbcTemplate.query(SELECT_FROM_OCR_WORD, ocrWordMapper());

	assertEquals(expected, results);
    }

    @Test
    void testUpdateCorrectTextInOcrWord() {
	// given
	OcrWordEntity ocrWord1 = getOcrWord(1, 1, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord2 = getOcrWord(1, 1, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord3 = getOcrWord(1, 1, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);

	OcrWordEntity ocrWord2_1 = getOcrWord(1, 1, 111, 222, 333, 444, 555.555f, "DEF456", 2);

	List<OcrWordEntity> expected = Arrays.asList(ocrWord1, ocrWord2_1, ocrWord3);

	testClass.addOcrWord(ocrWord1);
	testClass.addOcrWord(ocrWord2);
	testClass.addOcrWord(ocrWord3);

	// when
	testClass.updateCorrectTextInOcrWord(new OcrWordId(1, 1, 2), "I have changed", new UserDetails());

	// then
	List<OcrWordEntity> results = jdbcTemplate.query(SELECT_FROM_OCR_WORD, ocrWordMapper());

	assertEquals(expected, results);
    }

    @Test
    void testMarkWordAsIgnored() {
	// given
	OcrWordEntity ocrWord1 = getOcrWord(1, 1, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord2 = getOcrWord(1, 1, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord3 = getOcrWord(1, 1, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);

	testClass.addOcrWord(ocrWord1);
	testClass.addOcrWord(ocrWord2);
	testClass.addOcrWord(ocrWord3);

	// when
	int result = testClass.markWordAsIgnored(new OcrWordId(1, 1, 2), new UserDetails());

	// then
	assertEquals(1, result);
	List<OcrWordEntity> words = jdbcTemplate.query(SELECT_FROM_OCR_WORD, ocrWordMapper());
	assertEquals(Arrays.asList(ocrWord1, ocrWord2, ocrWord3), words);
    }

    @Test
    void testGetWords() {
	// given
	OcrWordEntity ocrWord1 = getOcrWord(1, 1, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord2 = getOcrWord(1, 1, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord3 = getOcrWord(1, 1, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);
	OcrWordEntity ocrWord4 = getOcrWord(1, 2, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord5 = getOcrWord(1, 2, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord6 = getOcrWord(1, 2, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);
	OcrWordEntity ocrWord7 = getOcrWord(2, 2, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord8 = getOcrWord(2, 2, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord9 = getOcrWord(2, 2, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);

	List<OcrWordEntity> toBeInserted = Arrays.asList(ocrWord1, ocrWord2, ocrWord3, ocrWord4, ocrWord5, ocrWord6, ocrWord7, ocrWord8, ocrWord9);

	List<OcrWordEntity> expected = Arrays.asList(ocrWord1, ocrWord3);

	toBeInserted.forEach(ocrWord -> testClass.addOcrWord(ocrWord));

	// when
	Collection<OcrWordOutputDto> results = testClass.getWords(1, 1, new UserDetails());

	// then
	assertEquals(expected, results);
    }

    @Test
    void testGetWordCount() {
	// given
	OcrWordEntity ocrWord1 = getOcrWord(1, 1, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord2 = getOcrWord(1, 1, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord3 = getOcrWord(1, 1, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);
	OcrWordEntity ocrWord4 = getOcrWord(1, 2, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord5 = getOcrWord(1, 2, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord6 = getOcrWord(1, 2, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);
	OcrWordEntity ocrWord7 = getOcrWord(2, 2, 11, 22, 33, 44, 55.55f, "ABC123", 1);
	OcrWordEntity ocrWord8 = getOcrWord(2, 2, 111, 222, 333, 444, 555.555f, "DEF456", 2);
	OcrWordEntity ocrWord9 = getOcrWord(2, 2, 1111, 2222, 3333, 4444, 5555.5555f, "GHI789", 3);

	List<OcrWordEntity> toBeInserted = Arrays.asList(ocrWord1, ocrWord2, ocrWord3, ocrWord4, ocrWord5, ocrWord6, ocrWord7, ocrWord8, ocrWord9);

	toBeInserted.forEach(ocrWord -> testClass.addOcrWord(ocrWord));

	// when
	int result = testClass.getWordCount(1, 1);

	// then
	assertEquals(2, result);
    }

    private RowMapper<OcrWordEntity> ocrWordMapper() {
	return (ResultSet res, int rowNum) -> {
	    OcrWordEntity ocrWord =
		    getOcrWord(1, 1, res.getInt("x1"), res.getInt("y1"), res.getInt("x2"), res.getInt("y2"), res.getFloat("confidence"), res.getString("raw_text"), res.getInt("word_sequence_id"));
	    return ocrWord;
	};
    }

    private OcrWordEntity getOcrWord(int bookId, int pageImageId, int x1, int y1, int x2, int y2, float confidence, String rawText, int wordSequenceId) {
	OcrWordEntity ocrWord = new OcrWordEntity();
	ocrWord.setX1(x1);
	ocrWord.setY1(y1);
	ocrWord.setX2(x2);
	ocrWord.setY2(y2);
	ocrWord.setConfidence(confidence);
	ocrWord.setRawText(rawText);
	ocrWord.setOcrWordId(new OcrWordId(bookId, pageImageId, wordSequenceId));
	return ocrWord;
    }

}

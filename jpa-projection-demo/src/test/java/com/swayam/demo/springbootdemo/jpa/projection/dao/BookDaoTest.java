package com.swayam.demo.springbootdemo.jpa.projection.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.swayam.demo.springbootdemo.jpa.projection.model.Book;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void test() {
	// given
	EasyRandomParameters easyRandomParams = new EasyRandomParameters().excludeField(field -> field.getName().equals("id"));
	EasyRandom easyRandom = new EasyRandom(easyRandomParams);
	Book newBook = easyRandom.nextObject(Book.class);
	newBook.getChapters().forEach(chapter -> {
	    chapter.setBook(newBook);
	    chapter.getSections().forEach(section -> section.setChapter(chapter));
	});

	// when
	Book savedBook = bookDao.save(newBook);

	// then
	assertEquals(1L, savedBook.getId());
    }

}

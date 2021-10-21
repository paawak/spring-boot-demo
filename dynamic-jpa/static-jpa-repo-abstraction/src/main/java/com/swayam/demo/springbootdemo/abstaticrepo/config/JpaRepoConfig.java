package com.swayam.demo.springbootdemo.abstaticrepo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

import com.swayam.demo.springbootdemo.abstaticrepo.dao.AuthorDao;
import com.swayam.demo.springbootdemo.abstaticrepo.dao.BookDao;
import com.swayam.demo.springbootdemo.abstaticrepo.model.Author;
import com.swayam.demo.springbootdemo.abstaticrepo.model.Book;

@Configuration
public class JpaRepoConfig {

    @Bean
    public JpaRepositoryFactoryBean<AuthorDao, Author, Integer> authorRepository() {
	return new JpaRepositoryFactoryBean<>(AuthorDao.class);
    }

    @Bean
    public JpaRepositoryFactoryBean<BookDao, Book, Integer> bookRepository() {
	return new JpaRepositoryFactoryBean<>(BookDao.class);
    }

}

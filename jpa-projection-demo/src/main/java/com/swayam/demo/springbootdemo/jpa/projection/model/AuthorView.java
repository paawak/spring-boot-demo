package com.swayam.demo.springbootdemo.jpa.projection.model;

public record AuthorView(Long authorId, String firstName, Long chapterId, String title) {

}

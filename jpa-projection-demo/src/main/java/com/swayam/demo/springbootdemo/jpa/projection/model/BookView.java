package com.swayam.demo.springbootdemo.jpa.projection.model;

public record BookView(Long id, String title, Long authorId, String authorFirstName) {

}

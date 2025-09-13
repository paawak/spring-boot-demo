package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Section {

    private UUID id;

    private String sectionText;

    private String style;

    private int length;

}

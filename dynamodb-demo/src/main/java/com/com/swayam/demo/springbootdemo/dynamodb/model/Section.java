package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.util.UUID;

public record Section(UUID id, String sectionText, String style, int length) {

}

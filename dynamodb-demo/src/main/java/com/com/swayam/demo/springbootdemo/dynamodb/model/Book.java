package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class Book {

    @Getter(onMethod_ = { @DynamoDbPartitionKey })
    private String id;

    @Getter(onMethod_ = { @DynamoDbSortKey })
    private LocalDateTime publishedOn;

    private String title;

    private String description;

    private String authorId;
}

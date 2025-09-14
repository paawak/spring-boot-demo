package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
public class Author {

    @Getter(onMethod_ = { @DynamoDbPartitionKey })
    private UUID id;

    private String firstName;

    private String lastName;

}

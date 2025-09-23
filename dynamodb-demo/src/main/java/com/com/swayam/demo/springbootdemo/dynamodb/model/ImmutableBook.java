package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.time.LocalDateTime;

import lombok.Builder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbImmutable(builder = ImmutableBook.ImmutableBookBuilder.class)
@Builder
public record ImmutableBook(@DynamoDbPartitionKey String id, @DynamoDbSortKey LocalDateTime publishedOn, String title,
	String description, String authorId) {
}

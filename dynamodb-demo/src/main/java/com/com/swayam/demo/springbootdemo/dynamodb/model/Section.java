package com.com.swayam.demo.springbootdemo.dynamodb.model;

import java.util.UUID;

import lombok.Builder;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbImmutable(builder = Section.SectionBuilder.class)
@Builder
public record Section(@DynamoDbPartitionKey UUID id, String sectionText, String style, int length) {

}

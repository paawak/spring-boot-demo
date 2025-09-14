package com.com.swayam.demo.springbootdemo.dynamodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.com.swayam.demo.springbootdemo.dynamodb.model.Book;
import com.com.swayam.demo.springbootdemo.dynamodb.model.ImmutableBook;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
	return DynamoDbEnhancedClient.create();
    }

    @Bean
    public DynamoDbTable<Book> bookTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
	return dynamoDbEnhancedClient.table("book", TableSchema.fromBean(Book.class));
    }

    @Bean
    public DynamoDbTable<ImmutableBook> immutableBookTable(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
	return dynamoDbEnhancedClient.table("book", TableSchema.fromImmutableClass(ImmutableBook.class));
    }

}

package com.swayam.demo.springboot.googleauth.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.swayam.demo.springboot.googleauth.config.DynamicJpaRepositoryPostProcessor;
import com.swayam.demo.springboot.googleauth.repo.BookRepository;

import lombok.Value;

public class EntityClassUtil {

    private static final String DYNAMIC_PACKAGE_SUFFIX = ".dynamic.";
    private static final String DYNAMIC_ENTITY_PACKAGE = DynamicJpaRepositoryPostProcessor.ENTITY_PACKAGE + DYNAMIC_PACKAGE_SUFFIX;
    private static final String DYNAMIC_REPOSITORY_PACKAGE = BookRepository.class.getPackageName() + DYNAMIC_PACKAGE_SUFFIX;
    private static final String OCR_WORD_ENTITY_SUFFIX = "OcrWordEntity";
    private static final String CORRECTED_WORD_ENTITY_SUFFIX = "CorrectedWordEntity";
    private static final String OCR_WORD_ENTITY_REPOSITORY_SUFFIX = "OcrWordRepository";
    private static final String CORRECTED_WORD_ENTITY_REPOSITORY_SUFFIX = "CorrectedWordRepository";

    public EntityClassDetails getEntityClassDetails(String baseTableName) {

	String regex = "_[a-z]";

	Pattern pattern = Pattern.compile(regex);

	Matcher matcher = pattern.matcher(baseTableName);

	StringBuilder baseNameBuilder = new StringBuilder();

	while (matcher.find()) {
	    String originalToken = matcher.group();
	    String replacement = Character.toString(originalToken.charAt(1)).toUpperCase();
	    matcher.appendReplacement(baseNameBuilder, replacement);
	}

	matcher.appendTail(baseNameBuilder);

	String baseName = baseNameBuilder.replace(0, 1, Character.toString(baseNameBuilder.charAt(0)).toUpperCase()).toString();

	return new EntityClassDetails(DYNAMIC_ENTITY_PACKAGE + baseName + OCR_WORD_ENTITY_SUFFIX, DYNAMIC_ENTITY_PACKAGE + baseName + CORRECTED_WORD_ENTITY_SUFFIX,
		DYNAMIC_REPOSITORY_PACKAGE + baseName + OCR_WORD_ENTITY_REPOSITORY_SUFFIX, DYNAMIC_REPOSITORY_PACKAGE + baseName + CORRECTED_WORD_ENTITY_REPOSITORY_SUFFIX);
    }

    @Value
    public static class EntityClassDetails {

	private final String ocrWordEntity;
	private final String correctedWordEntity;
	private final String ocrWordEntityRepository;
	private final String correctedWordEntityRepository;

    }

}

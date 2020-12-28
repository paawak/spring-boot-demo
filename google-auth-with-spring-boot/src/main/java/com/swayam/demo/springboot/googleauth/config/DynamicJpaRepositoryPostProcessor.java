package com.swayam.demo.springboot.googleauth.config;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springboot.googleauth.model.old.Book;
import com.swayam.demo.springboot.googleauth.model.old.OcrWordEntityTemplate;
import com.swayam.demo.springboot.googleauth.repo.OcrWordRepositoryTemplate;
import com.swayam.demo.springboot.googleauth.service.EntityClassUtil;
import com.swayam.demo.springboot.googleauth.service.EntityClassUtil.EntityClassDetails;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

public class DynamicJpaRepositoryPostProcessor implements EnvironmentPostProcessor {

    public static final String ENTITY_PACKAGE = Book.class.getPackageName();

    private static final String OCR_WORD_TABLE_SUFFIX = "_ocr_word";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
	try {
	    createEntities(environment.getProperty("spring.datasource.url"), environment.getProperty("spring.datasource.username"), environment.getProperty("spring.datasource.password"));
	} catch (SQLException | IOException e) {
	    throw new RuntimeException(e);
	}
    }

    private void createEntities(String dbUrl, String dbUser, String dbPassword) throws SQLException, IOException {
	Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	PreparedStatement pStat = con.prepareStatement("SELECT base_table_name FROM book");
	ResultSet res = pStat.executeQuery();
	while (res.next()) {
	    String baseTableName = res.getString("base_table_name");
	    EntityClassDetails entityClassDetails = new EntityClassUtil().getEntityClassDetails(baseTableName);
	    createOcrWordEntity(baseTableName, entityClassDetails.getOcrWordEntity());
	    try {
		createOcrWordRepository(entityClassDetails.getOcrWordEntityRepository(), entityClassDetails.getOcrWordEntity());
	    } catch (ClassNotFoundException | IOException e) {
		throw new RuntimeException(e);
	    }
	}
    }

    private void createOcrWordEntity(String baseTableName, String entityClassName) throws IOException {

	if (classFileExists(entityClassName)) {
	    return;
	}

	new ByteBuddy().subclass(OcrWordEntityTemplate.class).annotateType(new Entity() {

	    @Override
	    public Class<? extends Annotation> annotationType() {
		return Entity.class;
	    }

	    @Override
	    public String name() {
		return "";
	    }
	}, new Table() {

	    @Override
	    public Class<? extends Annotation> annotationType() {
		return Table.class;
	    }

	    @Override
	    public UniqueConstraint[] uniqueConstraints() {
		return new UniqueConstraint[0];
	    }

	    @Override
	    public String schema() {
		return "";
	    }

	    @Override
	    public String name() {
		return baseTableName + OCR_WORD_TABLE_SUFFIX;
	    }

	    @Override
	    public Index[] indexes() {
		return new Index[0];
	    }

	    @Override
	    public String catalog() {
		return "";
	    }
	}).name(entityClassName).make().load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).saveIn(getBaseLocation());

    }

    private void createOcrWordRepository(String repositoryClassName, String entityClassName) throws IOException, ClassNotFoundException {
	if (classFileExists(repositoryClassName)) {
	    return;
	}
	Generic crudRepo = Generic.Builder.parameterizedType(CrudRepository.class, Class.forName(entityClassName), Long.class).build();
	new ByteBuddy().makeInterface(crudRepo).implement(OcrWordRepositoryTemplate.class).annotateType(new Repository() {

	    @Override
	    public Class<? extends Annotation> annotationType() {
		return Repository.class;
	    }

	    @Override
	    public String value() {
		return repositoryClassName;
	    }
	}).name(repositoryClassName).make().load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER).saveIn(getBaseLocation());

    }

    private boolean classFileExists(String className) {
	File baseLocation = getBaseLocation();
	File pathToClassFile = new File(baseLocation, className.replaceAll("\\.", "/") + ".class");
	return pathToClassFile.exists();
    }

    private File getBaseLocation() {
	File baseLocation;
	try {
	    baseLocation = new File(DynamicJpaRepositoryPostProcessor.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	} catch (URISyntaxException e) {
	    throw new RuntimeException(e);
	}
	return baseLocation;
    }

}

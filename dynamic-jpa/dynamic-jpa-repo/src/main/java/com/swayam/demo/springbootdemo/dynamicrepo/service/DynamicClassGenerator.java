package com.swayam.demo.springbootdemo.dynamicrepo.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springbootdemo.dynamicrepo.dao.BookDaoTemplate;
import com.swayam.demo.springbootdemo.dynamicrepo.model.BookTemplateImpl;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

public class DynamicClassGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicClassGenerator.class);

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     * 
     * <pre>
     * &#64;Entity
     * &#64;Table(name = "book")
     * public class Book extends BookTemplateImpl {
     * 
     * }
     * </pre>
     * 
     * @param entityClassName
     */
    public Optional<Class<?>> createJpaEntity(String entityClassName) {
	if (classFileExists(entityClassName)) {
	    LOG.info("The Entity class " + entityClassName + " already exists, not creating a new one");
	    return Optional.empty();
	}

	LOG.info("Creating new class: " + entityClassName);

	Unloaded<?> generatedClass = new ByteBuddy().subclass(BookTemplateImpl.class)
		.annotateType(AnnotationDescription.Builder.ofType(Entity.class).build(),
			AnnotationDescription.Builder.ofType(Table.class).define("name", "book").build())
		.name(entityClassName).make();

	return Optional.of(saveGeneratedClassAsFile(generatedClass));
    }

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     * 
     * <pre>
     * public interface BookDao extends BookDaoTemplate, CrudRepository&lt;Book, Integer&gt; {
     * 
     *     &#64;Override
     *     &#64;Transactional
     *     &#64;Modifying
     *     &#64;Query("update Book set author.id = :authorId where id = :bookId")
     *     int updateAuthor(int bookId, int authorId);
     * 
     * }
     * </pre>
     * 
     * @param repositoryClassName
     * @param entityClassName
     */
    public void createJpaRepository(Class<?> entityClass, String repositoryClassName) {
	if (classFileExists(repositoryClassName)) {
	    LOG.info("The Repository class " + repositoryClassName + " already exists, not creating a new one");
	    return;
	}
	Generic crudRepo = Generic.Builder.parameterizedType(CrudRepository.class, entityClass, Integer.class).build();

	Unloaded<?> generatedClass = new ByteBuddy().makeInterface(crudRepo).implement(BookDaoTemplate.class)
		.method(ElementMatchers.named("updateAuthor")).withoutCode()
		.annotateMethod(AnnotationDescription.Builder.ofType(Modifying.class).build())
		.annotateMethod(AnnotationDescription.Builder.ofType(Query.class)
			.define("value",
				"update " + entityClass.getSimpleName()
					+ " set author.id = :authorId where id = :bookId")
			.build())
		.name(repositoryClassName).make();

	saveGeneratedClassAsFile(generatedClass);
    }

    private boolean classFileExists(String className) {
	try {
	    Class.forName(className);
	    return true;
	} catch (ClassNotFoundException e) {
	    return false;
	}
    }

    private Class<?> saveGeneratedClassAsFile(Unloaded<?> unloadedClass) {

	Loaded<?> loadedClass = unloadedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

	try {
	    loadedClass.saveIn(new File("target/classes"));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

	return loadedClass.getLoaded();

    }

}

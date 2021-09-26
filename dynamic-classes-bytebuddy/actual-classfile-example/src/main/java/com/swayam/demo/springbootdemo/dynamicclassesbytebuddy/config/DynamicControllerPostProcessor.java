package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest.BankDetailController;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

@Order(Ordered.LOWEST_PRECEDENCE)
public class DynamicControllerPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
	createDynamicController();
    }

    private void createDynamicController() {

	String className = BankDetailController.class.getName() + "V2";

	System.out.println("Creating new class: " + className);

	Unloaded<?> generatedClass =
		new ByteBuddy().subclass(BankDetailController.class)
			.annotateType(AnnotationDescription.Builder.ofType(RestController.class).build(),
				AnnotationDescription.Builder.ofType(RequestMapping.class)
					.defineArray("value", new String[] { "/v2/bank-item" }).build())
			.name(className).make();

	Loaded<?> loadedClass =
		generatedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

	try {
	    Map<TypeDescription, File> map = loadedClass.saveIn(new File("target/classes"));
	    System.out.println("Successfully saved the newly created class in: " + map);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

}

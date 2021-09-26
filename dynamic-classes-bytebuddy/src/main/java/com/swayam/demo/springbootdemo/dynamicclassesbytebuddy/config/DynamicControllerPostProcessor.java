package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.config;

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
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassInjector;

@Order(Ordered.LOWEST_PRECEDENCE)
public class DynamicControllerPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
	System.err.println("*****" + application.getClassLoader());

	String className = BankDetailController.class.getName() + "V2";

	createDynamicController(application.getClassLoader(), className);

    }

    private byte[] createDynamicController(ClassLoader classLoader, String className) {

	System.out.println("Creating new class: " + className);

	Unloaded<?> generatedClass = new ByteBuddy().subclass(BankDetailController.class)
		.annotateType(AnnotationDescription.Builder.ofType(RestController.class).build(),
			AnnotationDescription.Builder.ofType(RequestMapping.class)
				.defineArray("value", new String[] { "/v2/bank-item" }).build())
		.name(className).make();

	ClassInjector.UsingReflection.ofSystemClassLoader().inject(generatedClass.getAllTypes());

	// generatedClass.load(classLoader, .ALLOW_EXISTING_TYPES);

	return null;

    }

}

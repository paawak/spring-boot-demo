package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.config;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest.BankDetailController;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
public class DynamicControllerPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicControllerPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	String className = BankDetailController.class.getName() + "V2";

	LOG.info("Creating new class: {}", className);

	Unloaded<?> generatedClass =
		new ByteBuddy().subclass(BankDetailController.class)
			.annotateType(AnnotationDescription.Builder.ofType(RestController.class).build(),
				AnnotationDescription.Builder.ofType(RequestMapping.class)
					.defineArray("value", new String[] { "/v2/bank-item" }).build())
			.name(className).make();

	Loaded<?> loadedClass =
		generatedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

	File baseLocation = new File("target/classes");

	try {
	    loadedClass.saveIn(baseLocation);
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}

    }

}

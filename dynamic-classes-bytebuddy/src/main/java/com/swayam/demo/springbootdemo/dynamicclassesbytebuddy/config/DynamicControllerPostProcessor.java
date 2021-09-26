package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.rest.BankDetailController;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

@Configuration
public class DynamicControllerPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	String className = BankDetailController.class.getName() + "V2";

	Class<?> clazz = createDynamicController(beanFactory.getClass().getClassLoader(), className);

	BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(clazz)
		.addConstructorArgReference("bankDetailServiceImpl");
	((DefaultListableBeanFactory) beanFactory).registerBeanDefinition("myController",
		beanDefinitionBuilder.getBeanDefinition());

    }

    private Class<?> createDynamicController(ClassLoader classLoader, String className) {

	System.out.println("Creating new class: " + className);

	Unloaded<?> generatedClass = new ByteBuddy().subclass(BankDetailController.class)
		.annotateType(AnnotationDescription.Builder.ofType(RestController.class).build(),
			AnnotationDescription.Builder.ofType(RequestMapping.class)
				.defineArray("value", new String[] { "/v2/bank-item" }).build())
		.name(className).make();

	generatedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

	try {
	    return Class.forName(className);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	}

    }

}

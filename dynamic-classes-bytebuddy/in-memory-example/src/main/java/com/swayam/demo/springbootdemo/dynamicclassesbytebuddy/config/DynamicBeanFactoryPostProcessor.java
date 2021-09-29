package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DynamicBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	String className = BankDetailController.class.getName() + "V2";

	Class<?> dynamicController = createDynamicController(className);

	BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(dynamicController)
		.addConstructorArgReference("bankDetailServiceImpl");
	((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(className,
		beanDefinitionBuilder.getBeanDefinition());

    }

    /***
     * Creates the below class dynamically and loads it into the ClassLoader,
     * without actually saving it on disk:
     * 
     * <pre>
     * &#64;RestController
     * &#64;RequestMapping("/v2/bank-item")
     * public class BankDetailControllerV2 extends BankDetailController {
     * 
     *     public BankDetailControllerV2(BankDetailService bankDetailService) {
     * 	super(bankDetailService);
     *     }
     * 
     * }
     * </pre>
     */
    private Class<?> createDynamicController(String className) {

	LOG.info("Creating new class: {}", className);

	Unloaded<?> generatedClass =
		new ByteBuddy().subclass(BankDetailController.class)
			.annotateType(AnnotationDescription.Builder.ofType(RestController.class).build(),
				AnnotationDescription.Builder.ofType(RequestMapping.class)
					.defineArray("value", new String[] { "/v2/bank-item" }).build())
			.name(className).make();

	generatedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

	LOG.info("Loaded the class {} successfully into the classloader {}", className, getClass().getClassLoader());

	try {
	    return Class.forName(className);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	}

    }

}

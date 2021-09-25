package com.swayam.demo.springbootdemo.dynamicclassesbytebuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DynamicClassesBytebuddyApplication {

    public static void main(String[] args) {
	SpringApplication.run(DynamicClassesBytebuddyApplication.class, args);
    }

    @Bean
    public Docket swaggerApiV1() {
	return docket("v1");
    }

    @Bean
    public Docket swaggerApiV2() {
	return docket("v2");
    }

    private Docket docket(String version) {
	ApiInfo apiInfo = new ApiInfoBuilder().title("Dynamic Classes Example " + version).version(version).contact(new Contact("Palash Ray", "https://palashray.com", "paawak@gmail.com")).build();
	return new Docket(DocumentationType.SWAGGER_2).groupName(version).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/" + version + "/*")).build().apiInfo(apiInfo);
    }

}

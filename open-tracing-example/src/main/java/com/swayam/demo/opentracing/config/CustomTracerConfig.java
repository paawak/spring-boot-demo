package com.swayam.demo.opentracing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.opentracing.Tracer;
import io.opentracing.contrib.spring.web.interceptor.TracingHandlerInterceptor;
import io.opentracing.contrib.web.servlet.filter.TracingFilter;
import io.opentracing.util.GlobalTracer;
import se.hirt.jmc.opentracing.DelegatingJfrTracer;

@Configuration
public class CustomTracerConfig implements WebMvcConfigurer {

    private final Tracer jfrTracer;

    @Autowired
    public CustomTracerConfig(Tracer tracer) {
        System.out.println("CustomTracerConfig.CustomTracerConfig(): " + tracer);
        jfrTracer = new DelegatingJfrTracer(tracer);
        GlobalTracer.register(jfrTracer);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TracingHandlerInterceptor(jfrTracer));
    }

    @Bean
    public FilterRegistrationBean<TracingFilter> tracingFilter() {
        TracingFilter tracingFilter = new TracingFilter(jfrTracer);
        FilterRegistrationBean<TracingFilter> filterRegistrationBean = new FilterRegistrationBean<>(tracingFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.setAsyncSupported(true);
        return filterRegistrationBean;
    }

}

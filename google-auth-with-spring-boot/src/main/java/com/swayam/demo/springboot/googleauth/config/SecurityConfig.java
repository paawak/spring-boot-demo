package com.swayam.demo.springboot.googleauth.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.swayam.demo.springboot.googleauth.repo.UserDetailsRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] URLS_TO_ALLOW_WITHOUT_AUTH = { "/v2/api-docs", "/configuration/**",
	    "/swagger-ui.html", "/swagger*/**", "/webjars/**", "/h2-console/**", "/actuator/**" };

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.cors(config -> config.configurationSource(corsConfigurationSource())).authorizeRequests()
		.antMatchers(URLS_TO_ALLOW_WITHOUT_AUTH).permitAll().anyRequest().authenticated().and()
		.addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class).csrf().disable().headers()
		.frameOptions().sameOrigin().httpStrictTransportSecurity().disable();
    }

    @Override
    protected AuthenticationManager authenticationManager() {
	return new GoogleAuthenticationManager(userDetailsRepository);
    }

    private CorsConfigurationSource corsConfigurationSource() {
	CorsConfiguration corsConfiguration = new CorsConfiguration();
	corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	corsConfiguration.addAllowedMethod(HttpMethod.GET);
	corsConfiguration.addAllowedMethod(HttpMethod.PUT);
	corsConfiguration.addAllowedMethod(HttpMethod.POST);
	corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
	corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
	corsConfiguration.addAllowedHeader("X-Requested-With");
	corsConfiguration.addAllowedHeader("Content-Type");
	corsConfiguration.addAllowedHeader("Accept");
	corsConfiguration.addAllowedHeader("Origin");
	corsConfiguration.addAllowedHeader("Authorization");
	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	source.registerCorsConfiguration("/**", corsConfiguration);
	return source;
    }

    private AuthenticationFilter authenticationFilter() {
	SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
	successHandler.setRedirectStrategy((request, response, url) -> {
	});
	AuthenticationConverter authenticationConverter = new AuthenticationTokenExtractor();

	RequestMatcher requestMatcher =
		new NegatedRequestMatcher(new OrRequestMatcher(Arrays.stream(URLS_TO_ALLOW_WITHOUT_AUTH)
			.map(pattern -> new AntPathRequestMatcher(pattern)).collect(Collectors.toList())));

	AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), authenticationConverter);
	filter.setRequestMatcher(requestMatcher);
	filter.setSuccessHandler(successHandler);
	return filter;
    }

}

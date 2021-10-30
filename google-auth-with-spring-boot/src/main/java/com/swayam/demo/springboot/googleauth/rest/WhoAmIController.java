package com.swayam.demo.springboot.googleauth.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.UserDetails;

@RestController
@RequestMapping("/whoami")
public class WhoAmIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoAmIController.class);

    @GetMapping
    public UserDetails whoAmIGet(Authentication authentication) {
	LOGGER.debug("Authentication for GET: {}", authentication);
	return (UserDetails) authentication.getPrincipal();
    }

    @PostMapping
    public UserDetails whoAmIPost() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	LOGGER.debug("Authentication from POST: {}", authentication);
	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	return userDetails;
    }

}

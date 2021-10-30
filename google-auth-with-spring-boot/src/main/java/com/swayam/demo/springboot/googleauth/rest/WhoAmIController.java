package com.swayam.demo.springboot.googleauth.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swayam.demo.springboot.googleauth.model.UserDetails;

@RestController
@RequestMapping("/whoami")
public class WhoAmIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhoAmIController.class);

    @GetMapping
    public UserDetails applyCorrectionToOcrWords(Authentication authentication) {
	LOGGER.debug("Authentication: {}", authentication);
	return (UserDetails) authentication.getPrincipal();
    }

}

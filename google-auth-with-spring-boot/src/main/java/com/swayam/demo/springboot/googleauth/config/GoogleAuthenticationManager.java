package com.swayam.demo.springboot.googleauth.config;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.swayam.demo.springboot.googleauth.model.old.UserDetails;
import com.swayam.demo.springboot.googleauth.repo.UserDetailsRepository;
import com.swayam.demo.springboot.googleauth.service.GoogleTokenVerifier;

public class GoogleAuthenticationManager implements AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAuthenticationManager.class);

    private final UserDetailsRepository userDetailsRepository;

    public GoogleAuthenticationManager(UserDetailsRepository userDetailsRepository) {
	this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) {
	LOGGER.info("start authentication...");

	Payload payload = new GoogleTokenVerifier().verifyToken((String) authentication.getCredentials());

	String email = payload.getEmail();

	Optional<UserDetails> optUserDetails = userDetailsRepository.findByEmail(email);

	if (optUserDetails.isEmpty()) {
	    throw new AuthenticationServiceException("User not registered");
	}

	UserDetails userDetails = optUserDetails.get();

	UsernamePasswordAuthenticationToken token =
		new UsernamePasswordAuthenticationToken(userDetails.getName(), "DontBotherBro", Arrays.asList(new SimpleGrantedAuthority(userDetails.getRole().name())));
	token.setDetails(userDetails);
	return token;
    }

}

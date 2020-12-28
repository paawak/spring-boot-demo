package com.swayam.demo.springboot.googleauth.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class GoogleTokenVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleTokenVerifier.class);

    // TODO FIXME: move this to config file
    private static final String CLIENT_ID = "955630342713-55eu6b3k5hmsg8grojjmk8mj1gi47g37.apps.googleusercontent.com";

    public Payload verifyToken(String authToken) {

	HttpTransport transport = new NetHttpTransport();
	JsonFactory jsonFactory = new JacksonFactory();
	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(Collections.singletonList(CLIENT_ID)).build();

	GoogleIdToken googleToken;

	try {
	    googleToken = verifier.verify(authToken);
	} catch (GeneralSecurityException | IOException e) {
	    LOGGER.warn("authentication failed while decoding token");
	    throw new AuthenticationServiceException("Error verifying auth token", e);
	}

	if (googleToken == null) {
	    LOGGER.warn("authentication failed: no token");
	    throw new BadCredentialsException("Invalid token");
	}

	return googleToken.getPayload();

    }

}

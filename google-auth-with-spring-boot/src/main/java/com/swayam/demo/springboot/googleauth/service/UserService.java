package com.swayam.demo.springboot.googleauth.service;

import com.swayam.demo.springboot.googleauth.model.old.UserDetails;

public interface UserService {

    UserDetails doNewRegistration(String authenticationToken);

}

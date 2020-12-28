package com.swayam.demo.springboot.googleauth.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.swayam.demo.springboot.googleauth.model.old.UserDetails;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {

    Optional<UserDetails> findByEmail(String email);

}

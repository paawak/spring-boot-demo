package com.swayam.demo.springbootdemo.ecs.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swayam.demo.springbootdemo.ecs.model.BankDetails;

public interface BankDetailsRepo extends JpaRepository<BankDetails, Long> {

}

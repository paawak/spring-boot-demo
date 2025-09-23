package com.swayam.demo.springbootdemo.testcontainer.cassandra.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.swayam.demo.springbootdemo.testcontainer.cassandra.model.BankDetail;

@Repository
public interface BankDetailRepository extends ListCrudRepository<BankDetail, Integer> {

}

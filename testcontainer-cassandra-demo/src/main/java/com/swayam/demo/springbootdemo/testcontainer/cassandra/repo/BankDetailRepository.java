package com.swayam.demo.springbootdemo.testcontainer.cassandra.repo;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.repository.ListCrudRepository;

import com.swayam.demo.springbootdemo.testcontainer.cassandra.model.BankDetail;

public interface BankDetailRepository extends ListCrudRepository<BankDetail, MapId> {

}

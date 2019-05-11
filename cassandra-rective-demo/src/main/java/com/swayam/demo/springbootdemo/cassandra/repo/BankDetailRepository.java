package com.swayam.demo.springbootdemo.cassandra.repo;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.swayam.demo.springbootdemo.cassandra.model.BankDetail;

public interface BankDetailRepository extends ReactiveCassandraRepository<BankDetail, MapId> {

}

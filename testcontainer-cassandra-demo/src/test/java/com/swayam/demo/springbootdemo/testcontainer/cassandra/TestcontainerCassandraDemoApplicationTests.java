package com.swayam.demo.springbootdemo.testcontainer.cassandra;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TestcontainerCassandraDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}

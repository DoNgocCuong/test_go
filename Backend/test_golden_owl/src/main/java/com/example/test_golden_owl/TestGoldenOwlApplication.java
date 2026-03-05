package com.example.test_golden_owl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TestGoldenOwlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestGoldenOwlApplication.class, args);
	}

}

package com.base.basesetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync
@SpringBootApplication
public class BasesetupApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasesetupApplication.class, args);
	}

}

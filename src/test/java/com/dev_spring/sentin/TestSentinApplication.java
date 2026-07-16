package com.dev_spring.sentin;

import org.springframework.boot.SpringApplication;

public class TestSentinApplication {

	public static void main(String[] args) {
		SpringApplication.from(SentinApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

package com.example.decentralizedstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.decentralizedstorage")
public class DecentralizedStorageApplication {

	public static void main(String[] args) {

		SpringApplication.run(DecentralizedStorageApplication.class, args);

		System.out.println("chalk rha hai decentralized");
	};

}

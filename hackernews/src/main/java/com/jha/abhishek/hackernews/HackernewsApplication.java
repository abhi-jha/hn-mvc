package com.jha.abhishek.hackernews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class HackernewsApplication {
	@Autowired
	DataSource dataSource;


	public static void main(String[] args) {
		SpringApplication.run(HackernewsApplication.class, args);
//		SpringApplication.run(HackernewsApplication.class, args);
//		SpringApplication.run(HackernewsApplication.class, args);
	}
}

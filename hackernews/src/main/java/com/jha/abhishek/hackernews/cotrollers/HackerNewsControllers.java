package com.jha.abhishek.hackernews.cotrollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HackerNewsControllers {
	@RequestMapping("/hello")
	public String t() {
		return "Hello World!";
	}
}

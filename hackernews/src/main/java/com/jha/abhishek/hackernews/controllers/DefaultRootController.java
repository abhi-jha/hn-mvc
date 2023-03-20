package com.jha.abhishek.hackernews.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Hidden
public class DefaultRootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultResponse() {
		return "redirect:" + "http://www.productioncompilers.com/swagger-ui.html";
	}
}

package com.jha.abhishek.hackernews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class DefaultRootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultResponse() {
		return "redirect:" + "http://www.productioncompilers.com/swagger-ui.html";
	}
}

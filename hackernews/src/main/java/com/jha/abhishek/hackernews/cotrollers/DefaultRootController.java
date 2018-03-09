package com.jha.abhishek.hackernews.cotrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultRootController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultResponse() {
		return "redirect:" + "http://www.productioncompilers.com/swagger-ui.html";
	}
}

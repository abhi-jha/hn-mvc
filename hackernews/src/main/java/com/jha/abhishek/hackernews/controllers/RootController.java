package com.jha.abhishek.hackernews.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class RootController {
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public String defaultHelloResponse() {
        return "Hello World!";
    }
}

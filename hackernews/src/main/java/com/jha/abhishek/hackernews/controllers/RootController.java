package com.jha.abhishek.hackernews.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class RootController {
    @RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
    public String defaultHelloResponse() {
        return "Hello World!";
    }
}

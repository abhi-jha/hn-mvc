package com.jha.abhishek.hackernews.cotrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jha.abhishek.hackernews.entities.HackernewsStories;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.services.HackernewsServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="Tech news", description="Links for major tech news",  tags = { "REST API Links from major tech news" })
@RequestMapping("api/v1/")
public class HackerNewsControllers {
	@Autowired
	HackernewsServices hack;

	@ApiOperation ( value = "Hello there", response = String.class)
    @RequestMapping ( value = "/hello", method = RequestMethod.GET)
	public String t() {
		return "Hello World!";
	}

	@ApiOperation ( value = "Get a story corresponding to the id", response = HackernewsStories.class)
    @RequestMapping ( value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStory(@PathVariable("id") Long id) throws NonCriticalException,CriticalException {
		Optional<HackernewsStories> story = hack.findById(id);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}
}

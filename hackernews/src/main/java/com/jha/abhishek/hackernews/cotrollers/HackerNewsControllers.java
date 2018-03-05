package com.jha.abhishek.hackernews.cotrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jha.abhishek.hackernews.domains.HackernewsDomain;
import com.jha.abhishek.hackernews.domains.HackernewsDomainByUser;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.services.HackernewsServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Tech news", description = "Links for major tech news", tags = { "REST API Links from major tech news" })
@RequestMapping("api/v1/")
public class HackerNewsControllers {
	@Autowired
	HackernewsServices hack;

	@ApiOperation(value = "Get stories corresponding to minimum score", response = HackernewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/score/{score}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryForScore(@PathVariable("score") Long score)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsDomain>> story = hack.getByScore(score);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories where they all contain the string", response = HackernewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/match/text/{matchingText}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryforMatchingText(@PathVariable("matchingText") String matchingText)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsDomain>> story = hack.getByTitleContaining(matchingText);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories where they all contain a string and minimum score", response = HackernewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/match/text/{matchingText}/score/{score}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryforMatchingTextAndScoreGreater(@PathVariable("matchingText") String matchingText,
			@PathVariable("score") Long score) throws NonCriticalException, CriticalException {
		Optional<List<HackernewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThan(matchingText, score);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get stories by the same user", response = HackernewsDomainByUser.class, responseContainer = "List")
	@RequestMapping(value = "/match/user/{userId}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryByUser(@PathVariable("userId") String userId) throws NonCriticalException, CriticalException {
		Optional<List<HackernewsDomainByUser>> story = hack.getByBy(userId);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}
}

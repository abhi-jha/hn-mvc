package com.jha.abhishek.hackernews.cotrollers;

import java.util.Date;
import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.domains.NewsDomainByUser;
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

	@ApiOperation(value = "Get stories corresponding to minimum score", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/score/{score}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryForScore(@PathVariable("score") Long score)
			throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByScore(score);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories where they all contain the string", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/match/text/{matchingText}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryforMatchingText(@PathVariable("matchingText") String matchingText)
			throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTitleContaining(matchingText);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories where they all contain a string and minimum score", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/match/text/{matchingText}/score/{score}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryforMatchingTextAndScoreGreater(@PathVariable("matchingText") String matchingText,
			@PathVariable("score") Long score) throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThan(matchingText, score);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories by the same user", response = NewsDomainByUser.class, responseContainer = "List")
	@RequestMapping(value = "/match/user/{userId}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryByUser(@PathVariable("userId") String userId)
			throws NonCriticalException, CriticalException {
		Optional<List<NewsDomainByUser>> story = hack.getByBy(userId);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories for a day(ddMMyyyy)", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/time/{time}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryByADay(@PathVariable("time") @DateTimeFormat(pattern = "ddMMyyyy") Date date)
			throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTimeBetween(new Timestamp(date.getTime()),
				new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()));
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories for a day(ddMMyyyy) and minimum score", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/time/{time}/score/{score}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryByADayAndminimumScore(
			@PathVariable("time") @DateTimeFormat(pattern = "ddMMyyyy") Date date, @PathVariable("score") Long score)
			throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndScoreGreaterThan(new Timestamp(date.getTime()),
				new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), score);
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories for a day(ddMMyyyy), minimum score and a matching text", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/time/{time}/score/{score}/match/{matchingText}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryByADayAndminimumScoreAndmatchingText(
			@PathVariable("time") @DateTimeFormat(pattern = "ddMMyyyy") Date date, @PathVariable("score") Long score,
			@PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThanAndTimeBetween(matchingText,
				score, new Timestamp(date.getTime()),
				new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()));
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get stories for between(ddMMyyyy), minimum score and a matching text", response = NewsDomain.class, responseContainer = "List")
	@RequestMapping(value = "/time/{start}/{end}/score/{score}/match/{matchingText}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<?> getStoryBetweenTimeAndminimumScoreAndmatchingText(
			@PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date start,
			@PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date end, @PathVariable("score") Long score,
			@PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
		Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThanAndTimeBetween(matchingText,
				score, new Timestamp(start.getTime()), new Timestamp(end.getTime()));
		return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
	}
}

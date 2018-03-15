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
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Tech news", description = "Tech news link aggregation for better querying", tags = {"Web API Links"})
@RequestMapping("api/v1/")
public class HackerNewsControllers {
    @Autowired
    HackernewsServices hack;

    @ApiOperation(value = "Stories having above the score", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForScore(@PathVariable("score") Long score)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByScore(score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories containing a string", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/match/{matchingText}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryforMatchingText(@PathVariable("matchingText") String matchingText)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContaining(matchingText);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories where they all contain a string and above a score", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/match/{matchingText}/score/{score}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryforMatchingTextAndScoreGreater(@PathVariable("matchingText") String matchingText,
                                                                    @PathVariable("score") Long score) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThan(matchingText, score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    //DON'T LIST THIS ENDPOINT IN THE API DOCS; FIND A WORKAROUND TO AVOID DOING THIS
    @ApiOperation(value = "Stories by a user", response = NewsDomainByUser.class, responseContainer = "List")
    @RequestMapping(value = "/match/user/{userId}", produces = {"application/json"}, method = RequestMethod.GET)
    @ApiIgnore
    public ResponseEntity<?> getStoryByUser(@PathVariable("userId") String userId)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomainByUser>> story = hack.getByBy(userId);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories for a date(ddMMyyyy)", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{date}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByADay(@PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetween(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories between dates(ddMMyyyy)", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{start}/{end}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryBetweenATimePeriod(
            @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date startDate,
            @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date endDate)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetween(new Timestamp(startDate.getTime()),
                new Timestamp(endDate.getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories for a date(ddMMyyyy) and above a score", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{date}/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByADayAndMinimumScore(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date, @PathVariable("score") Long score)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndScoreGreaterThan(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories between dates(ddMMyyyy) and above a score", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{start}/{end}/score/{score}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryBetweenATimePeriodAndMinimumScore(
            @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date startDate,
            @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date endDate, @PathVariable("score") Long score)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndScoreGreaterThan(new Timestamp(startDate.getTime()),
                new Timestamp(endDate.getTime()), score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories for a date(ddMMyyyy), above a score and matching string", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{date}/score/{score}/match/{matchingText}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByADayAndMinimumScoreAndmatchingText(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date, @PathVariable("score") Long score,
            @PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThanAndTimeBetween(matchingText,
                score, new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories between dates(ddMMyyyy), above a score and a matching string", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{start}/{end}/score/{score}/match/{matchingText}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryBetweenTimeAndMinimumScoreAndMatchingText(
            @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date startDate,
            @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date endDate, @PathVariable("score") Long score,
            @PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThanAndTimeBetween(matchingText,
                score, new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories for a date(ddMMyyyy) and a matching string", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{date}/match/{matchingText}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForADayAndMatchingText(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date,
            @PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndTitleContaining(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), matchingText);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @ApiOperation(value = "Stories between dates(ddMMyyyy) and a matching string", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/date/{start}/{end}/match/{matchingText}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForADayAndMatchingText(
            @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date startDate,
            @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date endDate,
            @PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndTitleContaining(new Timestamp(startDate.getTime()),
                new Timestamp(endDate.getTime()), matchingText);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }
}

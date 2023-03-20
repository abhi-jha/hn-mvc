package com.jha.abhishek.hackernews.controllers;

import java.util.Date;
import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.domains.NewsDomainByUser;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.services.HackernewsServices;

@CrossOrigin
@RestController
@Tag(name = "Tech news", description = "Tech news link aggregation for better querying")
@RequestMapping("api/v1/")
public class HackerNewsControllers {
    @Autowired
    HackernewsServices hack;

    private static final String OK_MSG = "SUCCESSFUL OPERATION.";

    @Operation(description = "Stories having above the score",
            responses = {
            @ApiResponse(responseCode = "200", description = OK_MSG,
                    content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
    })
    @RequestMapping(value = "/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForScore(@PathVariable("score") Long score)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByScore(score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories containing a string",
            responses = {
                    @ApiResponse(responseCode = "200", description = OK_MSG,
                            content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
            })
    @RequestMapping(value = "/match/{matchingText}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryforMatchingText(@PathVariable("matchingText") String matchingText)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContaining(matchingText);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

//    @Operation(description = , responses = {@ApiResponse(responseCode = "200", description = OK_MSG,
//        content = @Content(schema = @Schema(implementation = NewsDomain.class)))})

    @Operation(description = "Stories where they all contain a string and above a score",
            responses = {
                    @ApiResponse(responseCode = "200", description = OK_MSG,
                            content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
            })
    @RequestMapping(value = "/match/{matchingText}/score/{score}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryforMatchingTextAndScoreGreater(@PathVariable("matchingText") String matchingText,
                                                                    @PathVariable("score") Long score) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTitleContainingAndScoreGreaterThan(matchingText, score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    //DON'T LIST THIS ENDPOINT IN THE API DOCS; FIND A WORKAROUND TO AVOID DOING THIS
    @Operation(description = "Stories by a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = OK_MSG,
                            content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomainByUser.class))))
            })
    @RequestMapping(value = "/match/user/{userId}", produces = {"application/json"}, method = RequestMethod.GET)
//    @Hidden
    public ResponseEntity<?> getStoryByUser(@PathVariable("userId") String userId)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomainByUser>> story = hack.getByBy(userId);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories by an id",
            responses = {
                    @ApiResponse(responseCode = "200", description = OK_MSG,
                            content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
            })
    @RequestMapping(value = "/match/id/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByUser(@PathVariable("id") Long id)
            throws NonCriticalException, CriticalException {
        Optional<NewsDomain> story = hack.getById(id);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories for a date(ddMMyyyy)",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/date/{date}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByADay(@PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetween(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories between dates(ddMMyyyy)",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/date/{start}/{end}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryBetweenATimePeriod(
            @PathVariable("start") @DateTimeFormat(pattern = "ddMMyyyy") Date startDate,
            @PathVariable("end") @DateTimeFormat(pattern = "ddMMyyyy") Date endDate)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetween(new Timestamp(startDate.getTime()),
                new Timestamp(endDate.getTime()));
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories for a date(ddMMyyyy) and above a score",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/date/{date}/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryByADayAndMinimumScore(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date, @PathVariable("score") Long score)
            throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndScoreGreaterThan(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), score);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories between dates(ddMMyyyy) and above a score",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
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

    @Operation(description = "Stories for a date(ddMMyyyy), above a score and matching string",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
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

    @Operation(description = "Stories between dates(ddMMyyyy), above a score and a matching string",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
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

    @Operation(description = "Stories for a date(ddMMyyyy) and a matching string",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/date/{date}/match/{matchingText}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForADayAndMatchingText(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date,
            @PathVariable("matchingText") String matchingText) throws NonCriticalException, CriticalException {
        Optional<List<NewsDomain>> story = hack.getByTimeBetweenAndTitleContaining(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), matchingText);
        return new ResponseEntity<>(story.isPresent() ? story.get() : Optional.empty(), HttpStatus.OK);
    }

    @Operation(description = "Stories between dates(ddMMyyyy) and a matching string",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
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

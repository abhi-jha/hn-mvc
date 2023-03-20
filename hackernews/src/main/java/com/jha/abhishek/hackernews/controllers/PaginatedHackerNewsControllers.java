package com.jha.abhishek.hackernews.controllers;

import com.jha.abhishek.hackernews.Utils.Utils;
import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.services.HackernewsServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@Tag(name = "Paginated Tech news", description = "Tech news link aggregation for better querying")
@RequestMapping("api/v1/paginate")
public class PaginatedHackerNewsControllers {
    private static final String OK_MSG = "SUCCESSFULLY OPERATION";
    @Autowired
    HackernewsServices hack;

    @Operation(description = "Records for a date",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/date/{date}", produces = {
            "application/json"}, method = RequestMethod.GET)
    public ResponseEntity getPaginatedByToday(
            @PathVariable("date") @DateTimeFormat(pattern = "ddMMyyyy") Date date,
            @QueryParam("offset") Integer offset,
            @QueryParam("limit") Integer limit,
            final HttpServletRequest request) throws NonCriticalException, CriticalException {
        if (offset == null)
            offset = new Integer(0);
        if (limit == null)
            limit = new Integer(20);
        Map resultMap = hack.getByDatePaginated(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), offset, limit, request);
        return ResponseEntity.ok().headers(Utils.getHeaders(resultMap)).body(resultMap.get("records"));
    }

    @Operation(description = "Stories having above the score",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })
    @RequestMapping(value = "/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForScore(@PathVariable("score") int score,
                                              @QueryParam("offset") Integer offset,
                                              @QueryParam("limit") Integer limit,
                                              final HttpServletRequest request)
            throws NonCriticalException, CriticalException {
        if (offset == null)
            offset = new Integer(0);
        if (limit == null)
            limit = new Integer(20);
        Map resultMap = hack.getByAboveScorePaginated(score, offset, limit, request);
        return ResponseEntity.ok().headers(Utils.getHeaders(resultMap)).body(resultMap.get("records"));
    }

    @Operation(description = "Stories containing a string",
        responses = {
                @ApiResponse(responseCode = "200", description = OK_MSG,
                        content = @Content(array = @ArraySchema(uniqueItems = false,schema = @Schema(implementation = NewsDomain.class))))
        })

    @RequestMapping(value = "/match/{matchingText}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForMatchingText(@PathVariable("matchingText") String text,
                                                     @QueryParam("offset") Integer offset,
                                                     @QueryParam("limit") Integer limit,
                                                     final HttpServletRequest request)
            throws NonCriticalException, CriticalException {
        if (offset == null)
            offset = new Integer(0);
        if (limit == null)
            limit = new Integer(20);
        Map resultMap = hack.getByPaginatedForTextMatch(text, offset, limit, request);
        return ResponseEntity.ok().headers(Utils.getHeaders(resultMap)).body(resultMap.get("records"));
    }
}

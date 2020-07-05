package com.jha.abhishek.hackernews.cotrollers;

import com.jha.abhishek.hackernews.Utils.Utils;
import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.services.HackernewsServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@Api(value = "Tech news", description = "Tech news link aggregation for better querying", tags = {"Paginated API Links"})
@RequestMapping("api/v1/paginate")
public class PaginatedHackerNewsControllers {
    @Autowired
    HackernewsServices hack;

    @ApiOperation(value = "Records for a date", response = NewsDomain.class, responseContainer = "List")
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
            limit = new Integer(10);
        Map resultMap = hack.getByDatePaginated(new Timestamp(date.getTime()),
                new Timestamp(new Date(date.getTime() + 3600 * 1000 * 24 - 1000).getTime()), offset, limit, request);
        return ResponseEntity.ok().headers(Utils.getHeaders(resultMap)).body(resultMap.get("records"));
    }

    @ApiOperation(value = "Stories having above the score", response = NewsDomain.class, responseContainer = "List")
    @RequestMapping(value = "/score/{score}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getStoryForScore(@PathVariable("score") int score,
                                              @QueryParam("offset") Integer offset,
                                              @QueryParam("limit") Integer limit,
                                              final HttpServletRequest request)
            throws NonCriticalException, CriticalException {
        if (offset == null)
            offset = new Integer(0);
        if (limit == null)
            limit = new Integer(10);
        Map resultMap = hack.getByAboveScorePaginated(score, offset, limit, request);
        return ResponseEntity.ok().headers(Utils.getHeaders(resultMap)).body(resultMap.get("records"));
    }
}

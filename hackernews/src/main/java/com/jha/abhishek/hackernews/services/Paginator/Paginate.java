package com.jha.abhishek.hackernews.services.Paginator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Link;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Paginate {

    public static final Integer MAX_LIMIT = 50;

    public static Map<String, Object> paginate(final int offset, int limit, final int totalRecords, final HttpServletRequest request) {

        Map pageLinks = new HashMap();

        limit = Math.min(limit, MAX_LIMIT);

        if (totalRecords > offset) {
            if (offset + limit < totalRecords) {
                final URI nextPageUri = getNextPage(offset, limit, request);
                final Optional<URI> nextPageUriOptional = Optional.ofNullable(nextPageUri);
                if (nextPageUriOptional.isPresent()) {
                    final Link nextPageLink = Link.fromUri(nextPageUri).build();
                    pageLinks.put("nextpage", nextPageLink);
                }
            }
            final URI prevPageUri = getPreviousPage(offset, limit, request);
            final Optional<URI> prevPageUriOptional = Optional.ofNullable(prevPageUri);
            if (prevPageUriOptional.isPresent()) {
                final Link prevPageLink = Link.fromUri(prevPageUri).build();
                pageLinks.put("prevpage", prevPageLink);
            }
            return pageLinks;
        }
        return pageLinks;
    }

    private static URI getNextPage(final Integer offset, final Integer limit, final HttpServletRequest request) {
        try {
            return new URI((request.getRequestURL().toString() + "?limit=" + limit + "&offset=" + (offset + limit)));
        } catch (Exception e) {
            return null;
        }
    }

    private static URI getPreviousPage(Integer offset, Integer limit, final HttpServletRequest request) {
        if (offset - limit >= 0) {
            offset = offset - limit;
        }
        else{
            limit = offset;
            offset = 0;
        }

        if (limit == 0 && offset == 0){
            return  null;
        }
        try {
            return new URI((request.getRequestURL().toString() + "?limit=" + limit + "&offset=" + (offset)));
        } catch (Exception e) {
            return null;
        }
    }
}
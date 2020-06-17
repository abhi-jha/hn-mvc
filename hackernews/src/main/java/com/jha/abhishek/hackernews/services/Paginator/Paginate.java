package com.jha.abhishek.hackernews.services.Paginator;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Link;
import java.net.URI;
import java.util.Optional;

public class Paginate {

    public static final Integer MAX_LIMIT = 50;

    public static Link paginate(final int offset, int limit, final int totalRecords, final HttpServletRequest request) {

        limit = Math.min(limit, MAX_LIMIT);

        if (totalRecords > offset) {
            if (offset + limit < totalRecords) {
                final URI uri = getNextPage(offset, limit, request);
                final Optional<URI> uriOptional = Optional.ofNullable(uri);
                if (uriOptional.isPresent()) {
                    final Link link = Link.fromUri(uri).build();
                    return link;
                }
            }
        }
        return null; //Should it really be null?
    }

    private static URI getNextPage(final Integer offset, final Integer limit, final HttpServletRequest request) {
        try {
            return new URI((request.getRequestURL().toString() + "?limit=" + limit + "&offset=" + (offset + limit)));
        } catch (Exception e) {
            return null;
        }
    }
}
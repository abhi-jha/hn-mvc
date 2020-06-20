package com.jha.abhishek.hackernews.Utils;

import org.springframework.http.HttpHeaders;

import javax.ws.rs.core.Link;
import java.util.Map;

public class Utils {

    public static HttpHeaders getHeaders(Map resultMap) {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Access-Control-Expose-Headers", "nextpage, prevpage");

        if (resultMap.get("nextpage") != null)
            headers.add("nextpage", ((Link) resultMap.get("nextpage")).getUri().toString());
        if (resultMap.get("prevpage") != null)
            headers.add("prevpage", ((Link) resultMap.get("prevpage")).getUri().toString());
        return headers;
    }
}

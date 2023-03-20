package com.jha.abhishek.hackernews.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.domains.NewsDomainByUser;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;

import jakarta.servlet.http.HttpServletRequest;

public interface HackernewsServices {
    public Optional<NewsDomain> getById(Long id);

	// Works for a single day as well if the end Timestamp is within 24 hours of the start Timestamp
    public Optional<List<NewsDomain>> getByTimeBetween(Timestamp start, Timestamp end)
            throws NonCriticalException, CriticalException;

    //Works for a single day as well if the end Timestamp is within 24 hours of the start Timestamp
    public Optional<List<NewsDomain>> getByTimeBetweenAndScoreGreaterThan(Timestamp start, Timestamp end, Long score)
            throws NonCriticalException, CriticalException;

    public Optional<List<NewsDomain>> getByTitleContainingAndScoreGreaterThanAndTimeBetween(String matchingText,
                                                                                            Long score, Timestamp start, Timestamp end) throws NonCriticalException, CriticalException;

    public Optional<List<NewsDomain>> getByScore(Long score) throws NonCriticalException, CriticalException;

    public Optional<List<NewsDomain>> getByTitleContaining(String matchingText)
            throws NonCriticalException, CriticalException;

    public Optional<List<NewsDomain>> getByTitleContainingAndScoreGreaterThan(String matchingText, Long score)
            throws NonCriticalException, CriticalException;

    public Optional<List<NewsDomainByUser>> getByBy(String by) throws NonCriticalException, CriticalException;

    //Works for a single day as well if the end Timestamp is within 24 hours of the start Timestamp
    public Optional<List<NewsDomain>> getByTimeBetweenAndTitleContaining(Timestamp start, Timestamp end, String title) throws NonCriticalException, CriticalException;

    public Map<String, Object> getByDatePaginated(Timestamp start, Timestamp end, Integer offset, Integer limit, final HttpServletRequest request) throws NonCriticalException, CriticalException;

    public Map<String, Object> getByAboveScorePaginated(int score, Integer offset, Integer limit, final HttpServletRequest request);

    public Map<String, Object> getByPaginatedForTextMatch(String text, Integer offset, Integer limit, final HttpServletRequest request);
}

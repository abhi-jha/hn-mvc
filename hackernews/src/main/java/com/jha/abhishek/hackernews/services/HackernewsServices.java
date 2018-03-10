package com.jha.abhishek.hackernews.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.domains.NewsDomainByUser;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;

public interface HackernewsServices {

	public Optional<List<NewsDomain>> getByTimeBetween(Timestamp start, Timestamp end)
			throws NonCriticalException, CriticalException;

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
}

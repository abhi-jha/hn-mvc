package com.jha.abhishek.hackernews.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.jha.abhishek.hackernews.domains.HackernewsDomain;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;

public interface HackernewsServices {
	
	public Optional<List<HackernewsDomain>> findByTime(Timestamp time)throws NonCriticalException, CriticalException;
	
	public Optional<List<HackernewsDomain>> findByScore(Long score)throws NonCriticalException, CriticalException;
	
	public Optional<List<HackernewsDomain>> findByTitleContaining(String matchingText)throws NonCriticalException, CriticalException;
	
	public Optional<List<HackernewsDomain>> findByTitleContainingAndScoreGreaterThan(String matchingText,Long score)throws NonCriticalException, CriticalException;
	
}

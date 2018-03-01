package com.jha.abhishek.hackernews.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import com.jha.abhishek.hackernews.entities.HackernewsStories;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;

public interface HackernewsServices {
	//TO DO : GLOBAL EXCEPTION HADLING
	public Optional<HackernewsStories> findById(Long id)throws NonCriticalException, CriticalException;
	
	public Optional<List<HackernewsStories>> findByTime(Timestamp time);
	
	public Optional<List<HackernewsStories>> findByScore(Long score);

}

package com.jha.abhishek.hackernews.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import com.jha.abhishek.hackernews.entities.HackernewsStories;

public interface HackernewsRepository extends Repository<HackernewsStories, Long> {
	
	Optional<HackernewsStories> findById(Long id);
	
	Optional<List<HackernewsStories>> findByScoreGreaterThan(Long score);
	
	Optional<List<HackernewsStories>> findByTitleContaining(String matchingText);
	
	Optional<List<HackernewsStories>> findByTitleContainingAndScoreGreaterThan(String matchingText,Long score);

}

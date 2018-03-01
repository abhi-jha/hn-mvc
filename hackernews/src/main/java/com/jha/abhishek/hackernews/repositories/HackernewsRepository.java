package com.jha.abhishek.hackernews.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import com.jha.abhishek.hackernews.entities.HackernewsStories;

public interface HackernewsRepository extends Repository<HackernewsStories, Long> {
	
	Optional<HackernewsStories> findById(Long id);

}

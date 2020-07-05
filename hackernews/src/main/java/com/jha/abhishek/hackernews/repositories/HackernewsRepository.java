package com.jha.abhishek.hackernews.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import com.jha.abhishek.hackernews.entities.HackernewsStories;

public interface HackernewsRepository extends Repository<HackernewsStories, Long> {

	Optional<HackernewsStories> findById(Long id);

	Optional<List<HackernewsStories>> findByScoreGreaterThan(Long score);

	Optional<List<HackernewsStories>> findByTitleContaining(String matchingText);

	Optional<List<HackernewsStories>> findByTitleContainingAndScoreGreaterThan(String matchingText, Long score);

	Optional<List<HackernewsStories>> findByBy(String by);

	Optional<List<HackernewsStories>> findByTimeBetween(Timestamp start, Timestamp end);

	Optional<List<HackernewsStories>> findByTimeBetweenAndScoreGreaterThan(Timestamp start, Timestamp end, Long score);

	Optional<List<HackernewsStories>> findByTitleContainingAndScoreGreaterThanAndTimeBetween(String matchingText,
			Long score, Timestamp start, Timestamp end);

	Optional<List<HackernewsStories>> findByTimeBetweenAndTitleContaining(Timestamp start, Timestamp end, String tiltle);

	@Query(value="select count(*) from DATA where time between :a and :b", nativeQuery = true)
	int getNumberOfRecordsForADate(Timestamp a, Timestamp b);

	@Query(value="select * from DATA where time between :a and :b order by score DESC limit :limit offset :offset", nativeQuery = true)
	Optional<List<HackernewsStories>> findByDate(Timestamp a, Timestamp b, int limit, int offset);

	@Query(value="select count(*) from DATA where score >= :score", nativeQuery = true)
	int getNumberOfRecordsForAboveAScore(int score);

	@Query(value="select * from DATA where score >= :score order by score DESC limit :limit offset :offset", nativeQuery = true)
	Optional<List<HackernewsStories>> findByPaginatedAboveAScore(int score, int limit, int offset);

}

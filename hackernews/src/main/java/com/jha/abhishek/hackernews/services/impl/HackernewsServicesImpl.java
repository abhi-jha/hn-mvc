package com.jha.abhishek.hackernews.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jha.abhishek.hackernews.domains.NewsDomain;
import com.jha.abhishek.hackernews.domains.NewsDomainByUser;
import com.jha.abhishek.hackernews.entities.HackernewsStories;
import com.jha.abhishek.hackernews.exceptionhandling.CriticalException;
import com.jha.abhishek.hackernews.exceptionhandling.NonCriticalException;
import com.jha.abhishek.hackernews.repositories.HackernewsRepository;
import com.jha.abhishek.hackernews.services.HackernewsServices;

@Service
public class HackernewsServicesImpl implements HackernewsServices {

	private static final Logger LOG = LoggerFactory.getLogger(HackernewsServicesImpl.class);

	private HackernewsRepository repository;

	@Autowired
	public void setRepository(HackernewsRepository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<NewsDomain> getById(Long id) {
		Optional<HackernewsStories> story = repository.findById(id);
		NewsDomain domain = new NewsDomain();
		setDomainvalues(domain, Optional.of(story.get()));
		return Optional.of(domain);
	}

	@Transactional
	@Override
	public Optional<List<NewsDomain>> getByTimeBetween(Timestamp start, Timestamp end)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByTimeBetween(start, end);

		return Optional.of(setvalues(stories));
	}

	@Transactional
	@Override
	public Optional<List<NewsDomain>> getByScore(Long score) throws NonCriticalException, CriticalException {

		Optional<List<HackernewsStories>> stories = repository.findByScoreGreaterThan(score);

		return Optional.of(setvalues(stories));
	}

	@Override
	public Optional<List<NewsDomain>> getByTitleContaining(String matchingText)
			throws NonCriticalException, CriticalException {

		Optional<List<HackernewsStories>> stories = repository.findByTitleContaining(matchingText);

		return Optional.of(setvalues(stories));
	}

	@Override
	public Optional<List<NewsDomain>> getByTitleContainingAndScoreGreaterThan(String matchingText, Long score)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByTitleContainingAndScoreGreaterThan(matchingText,
				score);

		return Optional.of(setvalues(stories));
	}

	@Override
	public Optional<List<NewsDomainByUser>> getByBy(String by) throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByBy(by);

		List<NewsDomainByUser> domains = new ArrayList<>();

		for (int i = 0; i < stories.get().size(); i++) {
			HackernewsStories story = stories.get().get(i);
			NewsDomainByUser domain = new NewsDomainByUser();
			setDomainvalues(domain, Optional.of(story));
			domain.setBy(story.getBy());
			domains.add(domain);

		}
		return Optional.of(domains);
	}

	@Override
	public Optional<List<NewsDomain>> getByTimeBetweenAndScoreGreaterThan(Timestamp start, Timestamp end, Long score)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByTimeBetweenAndScoreGreaterThan(start, end, score);

		return Optional.of(setvalues(stories));
	}

	@Override
	public Optional<List<NewsDomain>> getByTitleContainingAndScoreGreaterThanAndTimeBetween(String matchingText,
			Long score, Timestamp start, Timestamp end) throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository
				.findByTitleContainingAndScoreGreaterThanAndTimeBetween(matchingText, score, start, end);

		return Optional.of(setvalues(stories));
	}

	@Override
	public Optional<List<NewsDomain>> getByTimeBetweenAndTitleContaining(Timestamp start, Timestamp end, String title)
			throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByTimeBetweenAndTitleContaining(start, end, title);

		return Optional.of(setvalues(stories));
	}

	public List<NewsDomain> setvalues(Optional<List<HackernewsStories>> stories){
		List<NewsDomain> domains = new ArrayList<>();

		for (int i = 0; i < stories.get().size(); i++) {
			HackernewsStories story = stories.get().get(i);
			NewsDomain domain = new NewsDomain();
			setDomainvalues(domain, Optional.of(story));
			domains.add(domain);
		}

		return domains;
	}

	public void setDomainvalues(NewsDomain domain, Optional<HackernewsStories> story) {
		domain.setId(story.get().getId());
		domain.setScore(story.get().getScore());
		domain.setTime(story.get().getTime().getTime());
		domain.setTitle(story.get().getTitle());
		domain.setUrl(story.get().getUrl());
	}

}

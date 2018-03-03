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

import com.jha.abhishek.hackernews.domains.HackernewsDomain;
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

	@Transactional
	@Override
	public Optional<List<HackernewsDomain>> getByTime(Timestamp time)throws NonCriticalException, CriticalException {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Optional<List<HackernewsDomain>> getByScore(Long score) throws NonCriticalException, CriticalException{

		Optional<List<HackernewsStories>> stories = repository.findByScoreGreaterThan(score);

		List<HackernewsDomain> domains = new ArrayList<>();

		for (int i = 0; i < stories.get().size(); i++) {
			HackernewsStories story = stories.get().get(i);
			HackernewsDomain domain = new HackernewsDomain();
			setDomainvalues(domain, Optional.of(story));
			domains.add(domain);
		}
		return Optional.of(domains);
	}

	@Override
	public Optional<List<HackernewsDomain>> getByTitleContaining(String matchingText)throws NonCriticalException, CriticalException {

		Optional<List<HackernewsStories>> stories = repository.findByTitleContaining(matchingText);

		List<HackernewsDomain> domains = new ArrayList<>();

		for (int i = 0; i < stories.get().size(); i++) {
			HackernewsStories story = stories.get().get(i);
			HackernewsDomain domain = new HackernewsDomain();
			setDomainvalues(domain, Optional.of(story));
			domains.add(domain);
		}
		return Optional.of(domains);
	}

	@Override
	public Optional<List<HackernewsDomain>> getByTitleContainingAndScoreGreaterThan(String matchingText, Long score)throws NonCriticalException, CriticalException {
		Optional<List<HackernewsStories>> stories = repository.findByTitleContainingAndScoreGreaterThan(matchingText,
				score);

		List<HackernewsDomain> domains = new ArrayList<>();

		for (int i = 0; i < stories.get().size(); i++) {
			HackernewsStories story = stories.get().get(i);
			HackernewsDomain domain = new HackernewsDomain();
			setDomainvalues(domain, Optional.of(story));
			domains.add(domain);
		}
		return Optional.of(domains);
	}

	public void setDomainvalues(HackernewsDomain domain, Optional<HackernewsStories> story) {
		domain.setId(story.get().getId());
		domain.setScore(story.get().getScore());
		//Time t= new Time(story.get().getTime().getTime());
		//domain.setTime(t);
		domain.setTime(story.get().getTime().getTime());
		domain.setTitle(story.get().getTitle());
		domain.setUrl(story.get().getUrl());
	}

}

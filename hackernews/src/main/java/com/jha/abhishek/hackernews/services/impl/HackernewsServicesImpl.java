package com.jha.abhishek.hackernews.services.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Optional<HackernewsStories> findById(Long id)throws NonCriticalException,CriticalException {

//		if(id<1) {
//			throw new NonCriticalException("invalid id");
//		}
		Optional<HackernewsStories> hack;
		LOG.info("finding by : ", id);
		try {
			hack = repository.findById(id);
		}
		catch(Exception e) {
			LOG.info("hereglbnlkbgnglm");
			throw new NonCriticalException("invalid id");
		}
		
		LOG.info("returning story : ", hack.get().toString());

		return hack;
	}

	@Transactional
	@Override
	public Optional<List<HackernewsStories>> findByTime(Timestamp time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Optional<List<HackernewsStories>> findByScore(Long score) {
		// TODO Auto-generated method stub
		return null;
	}

}

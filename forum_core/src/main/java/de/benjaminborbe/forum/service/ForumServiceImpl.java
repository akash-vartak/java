package de.benjaminborbe.forum.service;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.forum.api.ForumService;

@Singleton
public class ForumServiceImpl implements ForumService {

	private final Logger logger;

	@Inject
	public ForumServiceImpl(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void execute() {
		logger.trace("execute");
	}

}
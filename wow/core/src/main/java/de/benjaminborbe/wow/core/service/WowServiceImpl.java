package de.benjaminborbe.wow.core.service;

import de.benjaminborbe.wow.api.WowService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WowServiceImpl implements WowService {

	private final Logger logger;

	@Inject
	public WowServiceImpl(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void execute() {
		logger.trace("execute");
	}

}

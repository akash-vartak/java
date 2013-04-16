package de.benjaminborbe.poker.service;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.cron.api.CronJob;
import de.benjaminborbe.poker.config.PokerConfig;
import de.benjaminborbe.poker.util.PokerAutoFolder;

@Singleton
public class PokerCronJob implements CronJob {

	/* s m h d m dw y */
	private static final String SCHEDULE_EXPRESSION = "*/15 * * * * ?";

	private final Logger logger;

	private final PokerConfig pokerConfig;

	private final PokerAutoFolder pokerAutoFolder;

	@Inject
	public PokerCronJob(final Logger logger, final PokerConfig pokerConfig, final PokerAutoFolder pokerAutoFolder) {
		this.logger = logger;
		this.pokerConfig = pokerConfig;
		this.pokerAutoFolder = pokerAutoFolder;
	}

	@Override
	public void execute() {
		if (pokerConfig.isCronEnabled()) {
			logger.trace("poker cron => started");
			pokerAutoFolder.run();
		}
		else {
			logger.trace("poker cron => skip");
		}
	}

	@Override
	public String getScheduleExpression() {
		return SCHEDULE_EXPRESSION;
	}

	@Override
	public boolean disallowConcurrentExecution() {
		return true;
	}

}
package de.benjaminborbe.confluence.service;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.confluence.config.ConfluenceConfig;
import de.benjaminborbe.confluence.util.ConfluenceRefresher;
import de.benjaminborbe.cron.api.CronJob;

@Singleton
public class ConfluenceRefreshCronJob implements CronJob {

	/* s m h d m dw y */
	private static final String SCHEDULE_EXPRESSION = "0 20 * * * ?"; // ones per hour

	private final ConfluenceRefresher confluenceRefresher;

	private final Logger logger;

	private final ConfluenceConfig confluenceConfig;

	@Inject
	public ConfluenceRefreshCronJob(final Logger logger, final ConfluenceConfig confluenceConfig, final ConfluenceRefresher confluenceRefresher) {
		this.logger = logger;
		this.confluenceConfig = confluenceConfig;
		this.confluenceRefresher = confluenceRefresher;
	}

	@Override
	public String getScheduleExpression() {
		return SCHEDULE_EXPRESSION;
	}

	@Override
	public void execute() {
		if (Boolean.TRUE.equals(confluenceConfig.getCronEnabled())) {
			logger.debug("confluence refresh cron => started");
			confluenceRefresher.refresh();
			logger.debug("confluence refresh cron => finished");
		}
		else {
			logger.debug("confluence refresh cron => skip");
		}
	}

	@Override
	public boolean disallowConcurrentExecution() {
		return true;
	}
}

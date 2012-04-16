package de.benjaminborbe.websearch.cron;

import java.net.URL;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.crawler.api.CrawlerInstruction;
import de.benjaminborbe.crawler.api.CrawlerInstructionBuilder;
import de.benjaminborbe.crawler.api.CrawlerService;
import de.benjaminborbe.cron.api.CronJob;
import de.benjaminborbe.tools.synchronize.RunOnlyOnceATime;
import de.benjaminborbe.tools.util.ThreadRunner;
import de.benjaminborbe.websearch.page.PageBean;
import de.benjaminborbe.websearch.util.UpdateDeterminer;

@Singleton
public class RefreshPagesCronJob implements CronJob {

	private final class RefreshRunnable implements Runnable {

		@Override
		public void run() {
			try {
				logger.debug("RefreshRunnable started");
				runOnlyOnceATime.run(new RefreshPages());
				logger.debug("RefreshRunnable finished");
			}
			catch (final Exception e) {
				logger.error(e.getClass().getSimpleName(), e);
			}
		}
	}

	private final class RefreshPages implements Runnable {

		@Override
		public void run() {
			try {
				logger.debug("refresh pages started");
				for (final PageBean page : updateDeterminer.determineExpiredPages()) {
					try {
						final URL url = page.getUrl();
						logger.debug("trigger refresh of url " + url.toExternalForm());
						final CrawlerInstruction crawlerInstruction = new CrawlerInstructionBuilder(url);
						crawlerService.processCrawlerInstruction(crawlerInstruction);
					}
					catch (final Exception e) {
						logger.error(e.getClass().getSimpleName(), e);
					}
				}
				logger.debug("refresh pages finished");
			}
			catch (final Exception e) {
				logger.error(e.getClass().getSimpleName(), e);
			}
		}
	}

	/* s m h d m dw y */
	private static final String SCHEDULE_EXPRESSION = "0 * * * * ?"; // ones per hour

	private final Logger logger;

	private final CrawlerService crawlerService;

	private final UpdateDeterminer updateDeterminer;

	private final RunOnlyOnceATime runOnlyOnceATime;

	private final ThreadRunner threadRunner;

	@Inject
	public RefreshPagesCronJob(
			final Logger logger,
			final UpdateDeterminer updateDeterminer,
			final CrawlerService crawlerService,
			final RunOnlyOnceATime runOnlyOnceATime,
			final ThreadRunner threadRunner) {
		this.logger = logger;
		this.updateDeterminer = updateDeterminer;
		this.crawlerService = crawlerService;
		this.runOnlyOnceATime = runOnlyOnceATime;
		this.threadRunner = threadRunner;
	}

	@Override
	public String getScheduleExpression() {
		return SCHEDULE_EXPRESSION;
	}

	@Override
	public void execute() {
		logger.debug("execute started");
		threadRunner.run("refreshpages", new RefreshRunnable());
		logger.debug("execute finished");
	}
}

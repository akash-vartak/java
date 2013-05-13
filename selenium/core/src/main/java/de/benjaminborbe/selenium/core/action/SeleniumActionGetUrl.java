package de.benjaminborbe.selenium.core.action;

import de.benjaminborbe.selenium.api.action.SeleniumActionConfigurationGetUrl;
import de.benjaminborbe.selenium.core.util.SeleniumExecutionProtocolImpl;
import org.openqa.selenium.WebDriver;

public class SeleniumActionGetUrl implements SeleniumAction<SeleniumActionConfigurationGetUrl> {

	@Override
	public Class<SeleniumActionConfigurationGetUrl> getType() {
		return SeleniumActionConfigurationGetUrl.class;
	}

	@Override
	public boolean execute(
		final WebDriver webDriver,
		final SeleniumExecutionProtocolImpl seleniumExecutionProtocol,
		final SeleniumActionConfigurationGetUrl seleniumActionConfiguration
	) {
		webDriver.get(seleniumActionConfiguration.getUrl().toExternalForm());
		seleniumExecutionProtocol.addInfo(seleniumActionConfiguration.getMessage());

		return true;
	}
}

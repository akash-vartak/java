package de.benjaminborbe.selenium.configuration.xml.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.selenium.configuration.xml.api.SeleniumConfigurationXmlService;
import de.benjaminborbe.selenium.configuration.xml.dao.SeleniumConfigurationXmlDao;
import de.benjaminborbe.selenium.configuration.xml.dao.SeleniumConfigurationXmlDaoStorage;
import de.benjaminborbe.selenium.configuration.xml.service.SeleniumConfigurationXmlServiceImpl;
import de.benjaminborbe.selenium.parser.SeleniumGuiActionXmlParser;
import de.benjaminborbe.selenium.parser.SeleniumGuiActionXmlParserImpl;
import de.benjaminborbe.selenium.parser.SeleniumGuiConfigurationXmlParser;
import de.benjaminborbe.selenium.parser.SeleniumGuiConfigurationXmlParserImpl;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;
import org.slf4j.Logger;

import javax.inject.Singleton;

public class SeleniumConfigurationXmlModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SeleniumConfigurationXmlDao.class).to(SeleniumConfigurationXmlDaoStorage.class).in(Singleton.class);
		bind(SeleniumConfigurationXmlService.class).to(SeleniumConfigurationXmlServiceImpl.class).in(Singleton.class);
		bind(SeleniumGuiActionXmlParser.class).to(SeleniumGuiActionXmlParserImpl.class);
		bind(SeleniumGuiConfigurationXmlParser.class).to(SeleniumGuiConfigurationXmlParserImpl.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}

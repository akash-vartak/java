package de.benjaminborbe.tools.guice;

import com.google.inject.AbstractModule;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.CalendarUtilImpl;
import de.benjaminborbe.tools.date.CurrentTime;
import de.benjaminborbe.tools.date.CurrentTimeImpl;
import de.benjaminborbe.tools.date.DateUtil;
import de.benjaminborbe.tools.date.DateUtilImpl;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.date.TimeZoneUtilImpl;
import de.benjaminborbe.tools.html.HtmlUtil;
import de.benjaminborbe.tools.html.HtmlUtilImpl;
import de.benjaminborbe.tools.jndi.JndiContext;
import de.benjaminborbe.tools.jndi.JndiContextImpl;
import de.benjaminborbe.tools.json.JSONParser;
import de.benjaminborbe.tools.json.JSONParserSimple;
import de.benjaminborbe.tools.log.LoggerSlf4Provider;
import de.benjaminborbe.tools.password.PasswordGenerator;
import de.benjaminborbe.tools.password.PasswordGeneratorImpl;
import de.benjaminborbe.tools.password.PasswordValidator;
import de.benjaminborbe.tools.password.PasswordValidatorImpl;
import de.benjaminborbe.tools.url.UrlUtil;
import de.benjaminborbe.tools.url.UrlUtilImpl;
import de.benjaminborbe.tools.util.Base64Util;
import de.benjaminborbe.tools.util.Base64UtilImpl;
import de.benjaminborbe.tools.util.IdGeneratorLong;
import de.benjaminborbe.tools.util.IdGeneratorLongImpl;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.util.ParseUtilImpl;
import de.benjaminborbe.tools.util.ResourceUtil;
import de.benjaminborbe.tools.util.ResourceUtilImpl;
import de.benjaminborbe.tools.util.StringUtil;
import de.benjaminborbe.tools.util.StringUtilImpl;
import org.slf4j.Logger;

import javax.inject.Singleton;

public abstract class ToolModuleBase extends AbstractModule {

	@Override
	protected void configure() {
		bind(JSONParser.class).to(JSONParserSimple.class).in(Singleton.class);
		bind(PasswordGenerator.class).to(PasswordGeneratorImpl.class).in(Singleton.class);
		bind(PasswordValidator.class).to(PasswordValidatorImpl.class).in(Singleton.class);
		bind(CurrentTime.class).to(CurrentTimeImpl.class);
		bind(UrlUtil.class).to(UrlUtilImpl.class).in(Singleton.class);
		bind(HtmlUtil.class).to(HtmlUtilImpl.class).in(Singleton.class);
		bind(ResourceUtil.class).to(ResourceUtilImpl.class).in(Singleton.class);
		bind(StringUtil.class).to(StringUtilImpl.class).in(Singleton.class);
		bind(JndiContext.class).to(JndiContextImpl.class).in(Singleton.class);
		bind(Base64Util.class).to(Base64UtilImpl.class).in(Singleton.class);
		bind(TimeZoneUtil.class).to(TimeZoneUtilImpl.class).in(Singleton.class);
		bind(ParseUtil.class).to(ParseUtilImpl.class).in(Singleton.class);
		bind(DateUtil.class).to(DateUtilImpl.class).in(Singleton.class);
		bind(CalendarUtil.class).to(CalendarUtilImpl.class).in(Singleton.class);
		bind(IdGeneratorLong.class).to(IdGeneratorLongImpl.class).in(Singleton.class);
		bind(Logger.class).toProvider(LoggerSlf4Provider.class).in(Singleton.class);
	}
}

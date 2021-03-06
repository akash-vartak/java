package de.benjaminborbe.poker.game;

import com.google.inject.Provider;
import de.benjaminborbe.poker.card.PokerCardIdentifierBuilder;
import de.benjaminborbe.poker.util.MapperPokerCardIdentifier;
import de.benjaminborbe.poker.util.MapperPokerCardIdentifierList;
import de.benjaminborbe.poker.util.MapperPokerGameIdentifier;
import de.benjaminborbe.poker.util.MapperPokerPlayerIdentifier;
import de.benjaminborbe.poker.util.MapperPokerPlayerIdentifierList;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.CalendarUtilImpl;
import de.benjaminborbe.tools.date.CurrentTime;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.date.TimeZoneUtilImpl;
import de.benjaminborbe.tools.guice.ProviderMock;
import de.benjaminborbe.tools.mapper.MapperBoolean;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperDouble;
import de.benjaminborbe.tools.mapper.MapperInteger;
import de.benjaminborbe.tools.mapper.MapperLong;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.util.ParseUtilImpl;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class PokerGameBeanMapperUnitTest {

	private final String fieldName;

	private final String fieldValue;

	public PokerGameBeanMapperUnitTest(final String fieldName, final String fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Parameters(name = "{index} - \"{0}\" = \"{1}\"")
	public static Collection<Object[]> generateData() {
		final List<Object[]> result = new ArrayList<Object[]>();
		result.add(new Object[]{PokerGameBeanMapper.ID, "1337"});
		result.add(new Object[]{PokerGameBeanMapper.NAME, "bla"});
		result.add(new Object[]{PokerGameBeanMapper.CREATED, "123456"});
		result.add(new Object[]{PokerGameBeanMapper.MODIFIED, "123456"});
		result.add(new Object[]{PokerGameBeanMapper.ACTIVE_POSITION_TIME, "123456"});
		result.add(new Object[]{PokerGameBeanMapper.RUNNING, "true"});
		result.add(new Object[]{PokerGameBeanMapper.SMALL_BLIND, "500"});
		result.add(new Object[]{PokerGameBeanMapper.BIG_BLIND, "1000"});
		result.add(new Object[]{PokerGameBeanMapper.ACTIVE_POSITION, "1"});
		result.add(new Object[]{PokerGameBeanMapper.BUTTON_POSITION, "2"});
		result.add(new Object[]{PokerGameBeanMapper.ROUND, "42"});
		result.add(new Object[]{PokerGameBeanMapper.BET, "23"});
		result.add(new Object[]{PokerGameBeanMapper.ACTIVE_PLAYERS, "1337"});
		result.add(new Object[]{PokerGameBeanMapper.PLAYERS, "1337"});
		result.add(new Object[]{PokerGameBeanMapper.CARDS, "HEARTS_TEN"});
		result.add(new Object[]{PokerGameBeanMapper.BOARD_CARDS, "HEARTS_TEN"});
		result.add(new Object[]{PokerGameBeanMapper.MAX_BID, "1000"});
		result.add(new Object[]{PokerGameBeanMapper.AUTO_FOLD_TIMEOUT, "1337"});
		result.add(new Object[]{PokerGameBeanMapper.MIN_RAISE_FACTOR, "1.0"});
		result.add(new Object[]{PokerGameBeanMapper.MAX_RAISE_FACTOR, "10.0"});
		result.add(new Object[]{PokerGameBeanMapper.CREDITS_NEGATIVE_ALLOWED, "true"});
		return result;
	}

	private PokerGameBeanMapper getPokerGameBeanMapper() {
		final Provider<PokerGameBean> beanProvider = new ProviderMock<PokerGameBean>(PokerGameBean.class);
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final TimeZoneUtil timeZoneUtil = new TimeZoneUtilImpl();
		final ParseUtil parseUtil = new ParseUtilImpl();

		final CurrentTime currentTime = EasyMock.createMock(CurrentTime.class);
		EasyMock.replay(currentTime);

		final CalendarUtil calendarUtil = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final MapperCalendar mapperCalendar = new MapperCalendar(timeZoneUtil, calendarUtil, parseUtil);
		final MapperString mapperString = new MapperString();
		final MapperPokerGameIdentifier mapperPokerGameIdentifier = new MapperPokerGameIdentifier();
		final MapperPokerPlayerIdentifier mapperPokerPlayerIdentifier = new MapperPokerPlayerIdentifier();
		final MapperBoolean mapperBoolean = new MapperBoolean(parseUtil);
		final MapperLong mapperLong = new MapperLong(parseUtil);
		final MapperDouble mapperDouble = new MapperDouble(parseUtil);
		final PokerCardIdentifierBuilder pokerCardIdentifierBuilder = new PokerCardIdentifierBuilder(logger, parseUtil);
		final MapperPokerCardIdentifier mapperPokerCardIdentifier = new MapperPokerCardIdentifier(pokerCardIdentifierBuilder);
		final MapperPokerCardIdentifierList mapperPokerCardIdentifierList = new MapperPokerCardIdentifierList(mapperPokerCardIdentifier);
		final MapperPokerPlayerIdentifierList mapperPokerPlayerIdentifierList = new MapperPokerPlayerIdentifierList(mapperPokerPlayerIdentifier);
		final MapperInteger mapperInteger = new MapperInteger(parseUtil);
		return new PokerGameBeanMapper(beanProvider, mapperPokerGameIdentifier, mapperCalendar, mapperString, mapperBoolean, mapperLong, mapperInteger, mapperDouble, mapperPokerCardIdentifierList,
			mapperPokerPlayerIdentifierList);
	}

	@Test
	public void testMaxRetryCounter() throws Exception {
		final PokerGameBeanMapper mapper = getPokerGameBeanMapper();
		final Map<String, String> inputData = new HashMap<String, String>();
		inputData.put(fieldName, fieldValue);
		final PokerGameBean bean = mapper.map(inputData);
		final Map<String, String> data = mapper.map(bean);
		assertThat(data.containsKey(fieldName), is(true));
		assertThat(data.get(fieldName), is(fieldValue));
	}

}

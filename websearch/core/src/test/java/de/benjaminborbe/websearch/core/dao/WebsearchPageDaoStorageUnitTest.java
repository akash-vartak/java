package de.benjaminborbe.websearch.core.dao;

import com.google.inject.Provider;
import de.benjaminborbe.httpdownloader.tools.HttpUtil;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.api.StorageValue;
import de.benjaminborbe.storage.memory.service.StorageServiceMemory;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.date.CalendarUtilImpl;
import de.benjaminborbe.tools.date.CurrentTime;
import de.benjaminborbe.tools.date.CurrentTimeImpl;
import de.benjaminborbe.tools.date.TimeZoneUtil;
import de.benjaminborbe.tools.date.TimeZoneUtilImpl;
import de.benjaminborbe.tools.guice.ProviderMock;
import de.benjaminborbe.tools.mapper.MapperByteArray;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperInteger;
import de.benjaminborbe.tools.mapper.MapperLong;
import de.benjaminborbe.tools.mapper.MapperUrl;
import de.benjaminborbe.tools.stream.ChannelTools;
import de.benjaminborbe.tools.stream.StreamUtil;
import de.benjaminborbe.tools.util.Base64Util;
import de.benjaminborbe.tools.util.Base64UtilImpl;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.tools.util.ParseUtilImpl;
import de.benjaminborbe.websearch.api.WebsearchPageIdentifier;
import de.benjaminborbe.websearch.core.util.MapperHttpContent;
import de.benjaminborbe.websearch.core.util.MapperHttpHeader;
import de.benjaminborbe.websearch.core.util.MapperWebsearchPageIdentifier;
import de.benjaminborbe.websearch.core.util.WebsearchAddToSearchIndex;
import de.benjaminborbe.websearch.core.util.WebsearchPageContentUpdateHandler;
import de.benjaminborbe.websearch.core.util.WebsearchParseLinks;
import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WebsearchPageDaoStorageUnitTest {

	@Test
	public void testCrud() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final StorageService storageService = new StorageServiceMemory(logger);
		final Provider<WebsearchPageBean> provider = new ProviderMock<WebsearchPageBean>(WebsearchPageBean.class);
		final WebsearchPageDaoSubPagesAction websearchPageDaoSubPagesAction = new WebsearchPageDaoSubPagesAction();
		final MapperWebsearchPageIdentifier mapperWebsearchPageIdentifier = new MapperWebsearchPageIdentifier();
		final CurrentTime currentTime = new CurrentTimeImpl();
		final ParseUtil parseUtil = new ParseUtilImpl();
		final TimeZoneUtil timeZoneUtil = new TimeZoneUtilImpl();
		final CalendarUtil calendarUtil = new CalendarUtilImpl(logger, currentTime, parseUtil, timeZoneUtil);
		final MapperCalendar mapperCalendar = new MapperCalendar(timeZoneUtil, calendarUtil, parseUtil);
		final MapperUrl mapperUrl = new MapperUrl(parseUtil);
		final Base64Util base64Util = new Base64UtilImpl();
		final MapperByteArray mapperByteArray = new MapperByteArray(base64Util);
		final MapperHttpHeader mapperHttpHeader = new MapperHttpHeader();
		final MapperInteger mapperInteger = new MapperInteger(parseUtil);
		final MapperLong mapperLong = new MapperLong(parseUtil);
		final MapperHttpContent mapperHttpContent = new MapperHttpContent(mapperByteArray);
		final WebsearchPageBeanMapper websearchPageBeanMapper = new WebsearchPageBeanMapper(provider, mapperCalendar, mapperWebsearchPageIdentifier, mapperUrl, mapperHttpHeader, mapperInteger, mapperHttpContent, mapperLong);
		final WebsearchPageIdentifierBuilder websearchPageIdentifierBuilder = null;

		final TimeZone timeZone = timeZoneUtil.getUTCTimeZone();
		final Calendar calendar = calendarUtil.parseDateTime(timeZone, "2012-12-24 20:15:59");
		final ChannelTools channelTools = new ChannelTools();
		final StreamUtil streamUtil = new StreamUtil(channelTools);
		final HttpUtil httpUtil = new HttpUtil(logger, streamUtil);
		final WebsearchAddToSearchIndex websearchAddToSearchIndex = EasyMock.createMock(WebsearchAddToSearchIndex.class);
		EasyMock.replay(websearchAddToSearchIndex);
		final WebsearchParseLinks websearchParseLinks = EasyMock.createMock(WebsearchParseLinks.class);
		EasyMock.replay(websearchParseLinks);

		final WebsearchPageContentUpdateHandler websearchPageContentUpdateHandler = new WebsearchPageContentUpdateHandler(logger, httpUtil, websearchAddToSearchIndex, websearchParseLinks);
		final WebsearchPageDaoStorage dao = new WebsearchPageDaoStorage(logger, storageService, provider, websearchPageDaoSubPagesAction, websearchPageBeanMapper,
			websearchPageIdentifierBuilder, calendarUtil, websearchPageContentUpdateHandler);
		final URL url = new URL("http://www.heise.de");
		final WebsearchPageIdentifier websearchPageIdentifier = new WebsearchPageIdentifier(url.toExternalForm());
		// create
		{
			final WebsearchPageBean bean = dao.create();
			bean.setId(websearchPageIdentifier);
			bean.setLastVisit(calendar);
			bean.setUrl(url);
			dao.save(bean);
		}

		// load
		{
			final WebsearchPageBean bean = dao.load(websearchPageIdentifier);
			assertEquals(calendar, bean.getLastVisit());
			assertEquals(url, bean.getUrl());
		}

		// delete
		{
			final WebsearchPageBean bean = dao.load(websearchPageIdentifier);
			dao.delete(bean);
		}

		// load
		{
			assertNull(dao.load(websearchPageIdentifier));
		}
	}

	@Test
	public void testOnPostSave() throws Exception {
		final String encoding = "UTF8";

		final StorageService storageService = EasyMock.createMock(StorageService.class);
		EasyMock.expect(storageService.getEncoding()).andReturn(encoding).anyTimes();
		EasyMock.replay(storageService);

		{
			final WebsearchPageBean entity = new WebsearchPageBean();

			final WebsearchPageContentUpdateHandler websearchPageContentUpdateHandler = EasyMock.createMock(WebsearchPageContentUpdateHandler.class);
			EasyMock.replay(websearchPageContentUpdateHandler);

			final WebsearchPageDaoStorage dao = new WebsearchPageDaoStorage(null, storageService, null, null, null, null, null, websearchPageContentUpdateHandler);
			final List<StorageValue> fieldNames = new ArrayList<StorageValue>();
			dao.onPostSave(entity, fieldNames);

			EasyMock.verify(websearchPageContentUpdateHandler);
		}

		{
			final WebsearchPageBean entity = new WebsearchPageBean();

			final WebsearchPageContentUpdateHandler websearchPageContentUpdateHandler = EasyMock.createMock(WebsearchPageContentUpdateHandler.class);
			websearchPageContentUpdateHandler.onContentUpdated(entity);
			EasyMock.replay(websearchPageContentUpdateHandler);

			final WebsearchPageDaoStorage dao = new WebsearchPageDaoStorage(null, storageService, null, null, null, null, null, websearchPageContentUpdateHandler);
			final List<StorageValue> fieldNames = null;
			dao.onPostSave(entity, fieldNames);

			EasyMock.verify(websearchPageContentUpdateHandler);
		}

		{
			final WebsearchPageBean entity = new WebsearchPageBean();

			final WebsearchPageContentUpdateHandler websearchPageContentUpdateHandler = EasyMock.createMock(WebsearchPageContentUpdateHandler.class);
			websearchPageContentUpdateHandler.onContentUpdated(entity);
			EasyMock.replay(websearchPageContentUpdateHandler);

			final WebsearchPageDaoStorage dao = new WebsearchPageDaoStorage(null, storageService, null, null, null, null, null, websearchPageContentUpdateHandler);
			final List<StorageValue> fieldNames = Arrays.asList(new StorageValue(WebsearchPageBeanMapper.CONTENT, encoding));
			dao.onPostSave(entity, fieldNames);

			EasyMock.verify(websearchPageContentUpdateHandler);
		}
	}
}

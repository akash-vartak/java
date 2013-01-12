package de.benjaminborbe.analytics.dao;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import de.benjaminborbe.analytics.api.AnalyticsReportIdentifier;
import de.benjaminborbe.analytics.api.AnalyticsReportInterval;
import de.benjaminborbe.analytics.api.AnalyticsReportValue;
import de.benjaminborbe.analytics.api.AnalyticsReportValueIterator;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.tools.util.ParseException;

public interface AnalyticsReportValueDao {

	AnalyticsReportValueIterator valueIterator(AnalyticsReportIdentifier analyticsReportIdentifier, AnalyticsReportInterval analyticsReportInterval) throws StorageException;

	void setReportValue(AnalyticsReportIdentifier analyticsReportIdentifier, AnalyticsReportInterval analyticsReportInterval, AnalyticsReportValue reportValue)
			throws StorageException, UnsupportedEncodingException, ParseException;

	void delete(AnalyticsReportIdentifier analyticsIdentifier, AnalyticsReportInterval analyticsReportInterval) throws StorageException;

	void delete(AnalyticsReportIdentifier analyticsIdentifier) throws StorageException;

	AnalyticsReportValueIterator valueIterator(AnalyticsReportValueIdentifier analyticsReportValueIdentifier) throws StorageException;

	void setReportValue(AnalyticsReportValueIdentifier analyticsReportValueIdentifier, AnalyticsReportValue reportValue) throws StorageException, UnsupportedEncodingException,
			ParseException;

	void delete(AnalyticsReportValueIdentifier analyticsReportValueIdentifier) throws StorageException;

	Double getReportValue(AnalyticsReportIdentifier id, AnalyticsReportInterval analyticsReportInterval, Calendar calendar) throws StorageException, UnsupportedEncodingException,
			ParseException;

	Double getReportValue(AnalyticsReportValueIdentifier analyticsReportValueIdentifier, Calendar calendar) throws StorageException, UnsupportedEncodingException, ParseException;
}

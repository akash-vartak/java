package de.benjaminborbe.dhl.dao;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.dhl.api.DhlIdentifier;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.DaoStorage;
import de.benjaminborbe.tools.date.CalendarUtil;

@Singleton
public class DhlDaoStorage extends DaoStorage<DhlBean, DhlIdentifier> implements DhlDao {

	private static final String COLUMN_FAMILY = "dhl";

	@Inject
	public DhlDaoStorage(
			final Logger logger,
			final StorageService storageService,
			final Provider<DhlBean> beanProvider,
			final DhlBeanMapper mapper,
			final DhlIdentifierBuilder identifierBuilder,
			final CalendarUtil calendarUtil) {
		super(logger, storageService, beanProvider, mapper, identifierBuilder, calendarUtil);
	}

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}

}
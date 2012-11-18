package de.benjaminborbe.gallery.dao;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import de.benjaminborbe.gallery.api.GalleryGroupIdentifier;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.DaoStorage;
import de.benjaminborbe.tools.date.CalendarUtil;

@Singleton
public class GalleryGroupDaoStorage extends DaoStorage<GalleryGroupBean, GalleryGroupIdentifier> implements GalleryGroupDao {

	@Inject
	public GalleryGroupDaoStorage(
			final Logger logger,
			final StorageService storageService,
			final Provider<GalleryGroupBean> beanProvider,
			final GalleryGroupBeanMapper mapper,
			final GalleryGroupIdentifierBuilder identifierBuilder,
			final CalendarUtil calendarUtil) {
		super(logger, storageService, beanProvider, mapper, identifierBuilder, calendarUtil);
	}

	private static final String COLUMN_FAMILY = "gallery_group";

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}

}

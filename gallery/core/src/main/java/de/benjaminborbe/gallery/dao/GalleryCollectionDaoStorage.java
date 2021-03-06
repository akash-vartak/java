package de.benjaminborbe.gallery.dao;

import com.google.inject.Provider;
import de.benjaminborbe.gallery.api.GalleryCollectionIdentifier;
import de.benjaminborbe.gallery.util.SharedPredicate;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.storage.api.StorageService;
import de.benjaminborbe.storage.tools.DaoStorage;
import de.benjaminborbe.storage.tools.EntityIterator;
import de.benjaminborbe.storage.tools.EntityIteratorFilter;
import de.benjaminborbe.tools.date.CalendarUtil;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GalleryCollectionDaoStorage extends DaoStorage<GalleryCollectionBean, GalleryCollectionIdentifier> implements GalleryCollectionDao {

	@Inject
	public GalleryCollectionDaoStorage(
		final Logger logger,
		final StorageService storageService,
		final Provider<GalleryCollectionBean> beanProvider,
		final GalleryCollectionBeanMapper mapper,
		final GalleryCollectionIdentifierBuilder identifierBuilder,
		final CalendarUtil calendarUtil
	) {
		super(logger, storageService, beanProvider, mapper, identifierBuilder, calendarUtil);
	}

	private static final String COLUMN_FAMILY = "gallery_collection";

	@Override
	protected String getColumnFamily() {
		return COLUMN_FAMILY;
	}

	@Override
	public EntityIterator<GalleryCollectionBean> getEntityIteratorShared() throws StorageException {
		return new EntityIteratorFilter<GalleryCollectionBean>(getEntityIterator(), new SharedPredicate<GalleryCollectionBean>());
	}

}

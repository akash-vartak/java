package de.benjaminborbe.confluence.dao;

import de.benjaminborbe.confluence.api.ConfluenceInstanceIdentifier;
import de.benjaminborbe.confluence.api.ConfluencePageIdentifier;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.storage.tools.Dao;
import de.benjaminborbe.storage.tools.EntityIterator;
import de.benjaminborbe.storage.tools.IdentifierIteratorException;

public interface ConfluencePageDao extends Dao<ConfluencePageBean, ConfluencePageIdentifier> {

	ConfluencePageBean findOrCreate(ConfluenceInstanceIdentifier id, String indexName, String pageId) throws StorageException;

	EntityIterator<ConfluencePageBean> getPagesOfInstance(ConfluenceInstanceIdentifier confluenceInstanceIdentifier) throws StorageException;

	long countPagesOfInstance(ConfluenceInstanceIdentifier confluenceInstanceIdentifier) throws StorageException;

	ConfluencePageIdentifier findPageByUrl(
		ConfluenceInstanceIdentifier confluenceInstanceIdentifier,
		String pageUrl
	) throws StorageException, IdentifierIteratorException;
}

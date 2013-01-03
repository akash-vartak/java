package de.benjaminborbe.index.util;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.index.api.IndexService;
import de.benjaminborbe.index.config.IndexConfig;
import de.benjaminborbe.index.service.IndexServiceDistributed;
import de.benjaminborbe.index.service.IndexServiceLucene;

@Singleton
public class IndexServiceFactory {

	private final IndexServiceDistributed indexServiceDistributed;

	private final IndexServiceLucene indexServiceLucene;

	private final IndexConfig indexConfig;

	@Inject
	public IndexServiceFactory(final IndexConfig indexConfig, final IndexServiceDistributed indexServiceDistributed, final IndexServiceLucene indexServiceLucene) {
		this.indexConfig = indexConfig;
		this.indexServiceDistributed = indexServiceDistributed;
		this.indexServiceLucene = indexServiceLucene;
	}

	public List<IndexService> getIndexServices() {
		final List<IndexService> indexServices = new ArrayList<IndexService>();
		if (indexConfig.isIndexDistributedEnabled()) {
			indexServices.add(indexServiceDistributed);
		}
		if (indexConfig.isIndexLuceneEnabled()) {
			indexServices.add(indexServiceLucene);
		}
		return indexServices;
	}
}

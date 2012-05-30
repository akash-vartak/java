package de.benjaminborbe.storage.util;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.configuration.api.ConfigurationService;

@Singleton
public class StorageConfigMock extends StorageConfigImpl implements StorageConfig {

	private int readLimit;

	private static final String CASSANDRA_KEYSPACE = "bb_test2";

	@Inject
	public StorageConfigMock(final ConfigurationService configurationService, final Logger logger) {
		super(logger, configurationService);
	}

	@Override
	public String getKeySpace() {
		return CASSANDRA_KEYSPACE;
	}

	@Override
	public int getReadLimit() {
		if (readLimit == 0)
			return super.getReadLimit();
		return readLimit;
	}

	public void setReadLimit(final int readLimit) {
		this.readLimit = readLimit;
	}

}

package de.benjaminborbe.storage.service;

import java.util.List;

import de.benjaminborbe.storage.api.PersistentStorageService;


public class PersistentStorageServiceMock extends StorageServiceMock implements PersistentStorageService {

	@Override
	public List<String> findByIdPrefix(String columnFamily, String prefix) {
		
		return null;
	}

	@Override
	public List<String> list(String columnFamily) {
		
		return null;
	}

}

package de.benjaminborbe.storage.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.cassandra.thrift.Cassandra.Iface;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.cassandra.utils.Hex;
import org.apache.thrift.TException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import com.google.inject.Inject;

public class StorageImporter {

	private final Logger logger;

	private final StorageConnectionPool storageConnectionPool;

	@Inject
	public StorageImporter(final Logger logger, final StorageConnectionPool storageConnectionPool) {
		this.logger = logger;
		this.storageConnectionPool = storageConnectionPool;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void importJson(final String keySpace, final String columnFamily, final String jsonContent) throws StorageConnectionPoolException, InvalidRequestException, TException,
			UnsupportedEncodingException, UnavailableException, TimedOutException, ParseException {
		StorageConnection connection = null;
		try {
			connection = storageConnectionPool.getConnection();
			final Iface client = connection.getClient(keySpace);

			logger.info("importJson - keySpace: " + keySpace + " columnfamily: " + columnFamily);

			final ColumnParent column_parent = new ColumnParent(columnFamily);
			final String encoding = "UTF-8";

			final JSONParser parser = new JSONParser();
			final Object object = parser.parse(jsonContent);
			if (object instanceof JSONObject) {
				final JSONObject root = (JSONObject) object;
				final Set<Entry> entries = root.entrySet();
				for (final Entry e : entries) {
					final Object keyObject = e.getKey();
					final Object fieldsObject = e.getValue();
					if (keyObject instanceof String && fieldsObject instanceof JSONArray) {
						final JSONArray fields = (JSONArray) fieldsObject;
						final byte[] key = Hex.hexToBytes((String) keyObject);

						for (final Object valuesObject : fields) {
							final JSONArray values = (JSONArray) valuesObject;
							if (values.size() == 3) {

								final String columnName = (String) values.get(0);
								final String columnValue = (String) values.get(1);
								final long timestamp = (Long) values.get(2);

								logger.info("insert key: " + new String(key, "UTF-8") + " cn: " + columnName + " cv: " + columnValue + " time: " + timestamp);
								final Column column = new Column(ByteBuffer.wrap(columnName.getBytes(encoding)));
								column.setValue(ByteBuffer.wrap(columnValue.getBytes(encoding)));
								column.setTimestamp(timestamp);
								final ConsistencyLevel consistency_level = ConsistencyLevel.ONE;

								client.insert(ByteBuffer.wrap(key), column_parent, column, consistency_level);
							}
						}
					}
				}
			}

		}
		finally {
			storageConnectionPool.releaseConnection(connection);
		}
	}
}

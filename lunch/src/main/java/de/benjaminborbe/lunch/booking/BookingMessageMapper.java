package de.benjaminborbe.lunch.booking;

import java.util.Calendar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.inject.Inject;

import de.benjaminborbe.tools.mapper.MapException;
import de.benjaminborbe.tools.mapper.MapperCalendar;

public class BookingMessageMapper {

	private static final String DATE = "date";

	private static final String CUSTOMER_NUMBER = "customerNumber";

	private final MapperCalendar mapperCalendar;

	@Inject
	public BookingMessageMapper(final MapperCalendar mapperCalendar) {
		this.mapperCalendar = mapperCalendar;
	}

	@SuppressWarnings("unchecked")
	public String map(final BookingMessage bookingMessage) throws MapException {
		try {
			final JSONObject object = new JSONObject();
			object.put(CUSTOMER_NUMBER, bookingMessage.getCustomerNumber());
			object.put(DATE, mapperCalendar.toString(bookingMessage.getDate()));
			return object.toJSONString();
		}
		catch (final Exception e) {
			throw new MapException(e);
		}
	}

	public BookingMessage map(final String message) throws MapException {
		try {
			final JSONParser parser = new JSONParser();
			final Object object = parser.parse(message);
			if (object instanceof JSONObject) {
				final JSONObject jsonobject = (JSONObject) object;
				final String user = getValue(jsonobject, CUSTOMER_NUMBER);
				final Calendar date = mapperCalendar.fromString(getValue(jsonobject, DATE));
				return new BookingMessage(user, date);
			}
			throw new MapException("not a json object");
		}
		catch (final ParseException e) {
			throw new MapException(e);
		}
	}

	private String getValue(final JSONObject jsonobject, final String name) {
		return jsonobject.get(name) != null ? String.valueOf(jsonobject.get(name)) : null;
	}
}

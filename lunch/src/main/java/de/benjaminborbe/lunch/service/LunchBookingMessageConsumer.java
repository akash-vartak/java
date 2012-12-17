package de.benjaminborbe.lunch.service;

import org.slf4j.Logger;

import com.google.inject.Inject;

import de.benjaminborbe.lunch.LunchConstants;
import de.benjaminborbe.lunch.booking.BookingMessage;
import de.benjaminborbe.lunch.booking.BookingMessageMapper;
import de.benjaminborbe.messageservice.api.Message;
import de.benjaminborbe.messageservice.api.MessageConsumer;
import de.benjaminborbe.tools.date.CalendarUtil;
import de.benjaminborbe.tools.mapper.MapException;

public class LunchBookingMessageConsumer implements MessageConsumer {

	private final Logger logger;

	private final BookingMessageMapper bookingMessageMapper;

	private final CalendarUtil calendarUtil;

	@Inject
	public LunchBookingMessageConsumer(final Logger logger, final BookingMessageMapper bookingMessageMapper, final CalendarUtil calendarUtil) {
		this.logger = logger;
		this.bookingMessageMapper = bookingMessageMapper;
		this.calendarUtil = calendarUtil;
	}

	@Override
	public String getType() {
		return LunchConstants.BOOKING_MESSAGE_TYPE;
	}

	@Override
	public boolean process(final Message message) {
		logger.debug("process");
		try {
			final BookingMessage bookingMessage = bookingMessageMapper.map(message.getContent());
			logger.debug("book - user: " + bookingMessage.getUser() + " date: " + calendarUtil.toDateString(bookingMessage.getDate()));
			return true;
		}
		catch (final MapException e) {
			logger.warn(e.getClass().getName(), e);
			return false;
		}
	}
}
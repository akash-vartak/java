package de.benjaminborbe.lunch.mock;

import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.kiosk.api.KioskUser;
import de.benjaminborbe.lunch.api.Lunch;
import de.benjaminborbe.lunch.api.LunchService;
import de.benjaminborbe.lunch.api.LunchServiceException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class LunchServiceMock implements LunchService {

	public LunchServiceMock() {
	}

	@Override
	public Collection<Lunch> getLunchs(final SessionIdentifier sessionIdentifier, final String fullname) {
		final List<Lunch> result = new ArrayList<Lunch>();
		final Lunch lunch = new Lunch() {

			@Override
			public String getName() {
				return "Pizza";
			}

			@Override
			public Date getDate() {
				return new Date();
			}

			@Override
			public boolean isSubscribed() {
				return true;
			}

			@Override
			public URL getUrl() {
				try {
					return new URL("http://example.com");
				} catch (final MalformedURLException e) {
					return null;
				}
			}
		};
		result.add(lunch);
		return result;
	}

	@Override
	public Collection<Lunch> getLunchs(final SessionIdentifier sessionIdentifier) throws LunchServiceException {
		return null;
	}

	@Override
	public Collection<Lunch> getLunchsArchiv(final SessionIdentifier sessionIdentifier, final String fullname) throws LunchServiceException {
		return null;
	}

	@Override
	public Collection<KioskUser> getSubscribeUser(final Calendar day) throws LunchServiceException, LoginRequiredException,
		PermissionDeniedException {
		return null;
	}

	@Override
	public Collection<KioskUser> getBookedUser(final Calendar day) throws LunchServiceException, LoginRequiredException,
		PermissionDeniedException {
		return null;
	}

	@Override
	public void book(
		final SessionIdentifier sessionIdentifier,
		final Calendar day,
		final Collection<Long> users
	) throws LunchServiceException, LoginRequiredException,
		PermissionDeniedException {
	}

	@Override
	public boolean isNotificationActivated(final UserIdentifier userIdentifier) throws LunchServiceException {
		return false;
	}

	@Override
	public void activateNotification(final UserIdentifier userIdentifier) throws LunchServiceException {
	}

	@Override
	public void deactivateNotification(final UserIdentifier userIdentifier) throws LunchServiceException {
	}

}

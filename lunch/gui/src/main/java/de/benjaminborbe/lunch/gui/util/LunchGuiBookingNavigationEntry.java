package de.benjaminborbe.lunch.gui.util;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.AuthorizationServiceException;
import de.benjaminborbe.authorization.api.PermissionIdentifier;
import de.benjaminborbe.lunch.api.LunchService;
import de.benjaminborbe.lunch.gui.LunchGuiConstants;
import de.benjaminborbe.navigation.api.NavigationEntry;

import javax.inject.Inject;

public class LunchGuiBookingNavigationEntry implements NavigationEntry {

	private final AuthorizationService authorizationService;

	@Inject
	public LunchGuiBookingNavigationEntry(final AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public String getTitle() {
		return "Mittagessen-Buchen";
	}

	@Override
	public String getURL() {
		return "/" + LunchGuiConstants.NAME + LunchGuiConstants.URL_BOOKING;
	}

	@Override
	public boolean isVisible(final SessionIdentifier sessionIdentifier) {
		try {
			final PermissionIdentifier roleIdentifier = authorizationService.createPermissionIdentifier(LunchService.PERMISSION_BOOKING);
			return authorizationService.hasPermission(sessionIdentifier, roleIdentifier);
		} catch (final AuthorizationServiceException e) {
			return false;
		}
	}

}

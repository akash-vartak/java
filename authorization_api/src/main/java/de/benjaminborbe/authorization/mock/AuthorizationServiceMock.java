package de.benjaminborbe.authorization.mock;

import java.util.Collection;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.LoginRequiredException;
import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.authorization.api.AuthorizationService;
import de.benjaminborbe.authorization.api.AuthorizationServiceException;
import de.benjaminborbe.authorization.api.PermissionDeniedException;
import de.benjaminborbe.authorization.api.PermissionIdentifier;
import de.benjaminborbe.authorization.api.RoleIdentifier;

@Singleton
public class AuthorizationServiceMock implements AuthorizationService {

	@Inject
	public AuthorizationServiceMock() {
	}

	@Override
	public boolean hasRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException, PermissionDeniedException {
	}

	@Override
	public boolean hasPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectPermission(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException,
			PermissionDeniedException {
		if (!hasPermission(sessionIdentifier, permissionIdentifier)) {
			throw new PermissionDeniedException("permission denied");
		}
	}

	@Override
	public boolean createRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public RoleIdentifier createRoleIdentifier(final String rolename) {
		return null;
	}

	@Override
	public boolean addUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws PermissionDeniedException,
			AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean removeUserRole(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier)
			throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean hasRole(final UserIdentifier userIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public Collection<RoleIdentifier> roleList() {
		return null;
	}

	@Override
	public PermissionIdentifier createPermissionIdentifier(final String permissionName) {
		return null;
	}

	@Override
	public Collection<PermissionIdentifier> permissionList() throws AuthorizationServiceException {
		return null;
	}

	@Override
	public boolean removePermissionRole(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier, final RoleIdentifier roleIdentifier)
			throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean addPermissionRole(final SessionIdentifier sessionIdentifier, final PermissionIdentifier permissionIdentifier, final RoleIdentifier roleIdentifier)
			throws PermissionDeniedException, AuthorizationServiceException {
		return false;
	}

	@Override
	public Collection<PermissionIdentifier> permissionList(final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return null;
	}

	@Override
	public boolean existsRole(final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public boolean existsPermission(final PermissionIdentifier permissionIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectAdminRole(final SessionIdentifier sessionIdentifier) throws AuthorizationServiceException, PermissionDeniedException {
	}

	@Override
	public void expectUser(final SessionIdentifier sessionIdentifier, final UserIdentifier userIdentifier) throws AuthorizationServiceException, PermissionDeniedException,
			LoginRequiredException {
	}

	@Override
	public boolean hasAdminRole(final SessionIdentifier sessionIdentifier) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public Collection<UserIdentifier> getUserWithRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException {
		return null;
	}

	@Override
	public void expectUser(final UserIdentifier currentUser, final UserIdentifier userIdentifier) throws AuthorizationServiceException, PermissionDeniedException {
	}

	@Override
	public void expectUser(final SessionIdentifier sessionIdentifier, final Collection<UserIdentifier> userIdentifiers) throws AuthorizationServiceException,
			PermissionDeniedException, LoginRequiredException {
	}

	@Override
	public void expectUser(final UserIdentifier currentUser, final Collection<UserIdentifier> userIdentifiers) throws AuthorizationServiceException, PermissionDeniedException {
	}

	@Override
	public void deleteRole(final SessionIdentifier sessionIdentifier, final RoleIdentifier roleIdentifier) throws AuthorizationServiceException, PermissionDeniedException,
			LoginRequiredException {
	}

	@Override
	public void expectOneOfRoles(final SessionIdentifier sessionIdentifier, final RoleIdentifier... roleIdentifiers) throws AuthorizationServiceException, PermissionDeniedException,
			LoginRequiredException {
	}

	@Override
	public boolean hasOneOfRoles(final SessionIdentifier sessionIdentifier, final RoleIdentifier... roleIdentifiers) throws AuthorizationServiceException {
		return false;
	}

	@Override
	public void expectOneOfPermissions(SessionIdentifier sessionIdentifier, PermissionIdentifier... permissionIdentifiers) throws AuthorizationServiceException,
			PermissionDeniedException {
	}

	@Override
	public boolean hasOneOfPermissions(SessionIdentifier sessionIdentifier, PermissionIdentifier... permissionIdentifiers) throws AuthorizationServiceException {
		return false;
	}

}

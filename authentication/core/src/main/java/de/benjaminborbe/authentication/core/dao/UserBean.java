package de.benjaminborbe.authentication.core.dao;

import de.benjaminborbe.authentication.api.User;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.storage.tools.EntityBase;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

import java.util.Calendar;
import java.util.TimeZone;

public class UserBean extends EntityBase<UserIdentifier> implements User, HasCreated, HasModified {

	private static final long serialVersionUID = -3922883715303844030L;

	private UserIdentifier id;

	private byte[] password;

	private byte[] passwordSalt;

	private String email;

	private String emailNew;

	private Boolean emailVerified;

	private String emailVerifyToken;

	private String fullname;

	private Boolean superAdmin;

	private Calendar modified;

	private Calendar created;

	private TimeZone timeZone;

	private Long loginCounter;

	private Calendar loginDate;

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(final byte[] password) {
		this.password = password;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Override
	public String getFullname() {
		return fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public byte[] getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(final byte[] passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	@Override
	public Boolean getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(final Boolean admin) {
		this.superAdmin = admin;
	}

	@Override
	public Calendar getModified() {
		return modified;
	}

	@Override
	public void setModified(final Calendar modified) {
		this.modified = modified;
	}

	@Override
	public Calendar getCreated() {
		return created;
	}

	@Override
	public void setCreated(final Calendar created) {
		this.created = created;
	}

	@Override
	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(final TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(final Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getEmailVerifyToken() {
		return emailVerifyToken;
	}

	public void setEmailVerifyToken(final String emailVerifyToken) {
		this.emailVerifyToken = emailVerifyToken;
	}

	public void increaseLoginCounter() {
		if (loginCounter == null) {
			loginCounter = 1l;
		} else {
			loginCounter = loginCounter + 1l;
		}
	}

	@Override
	public Long getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(final Long loginCounter) {
		this.loginCounter = loginCounter;
	}

	@Override
	public UserIdentifier getId() {
		return id;
	}

	@Override
	public void setId(final UserIdentifier id) {
		this.id = id;
	}

	@Override
	public Calendar getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(final Calendar loginDate) {
		this.loginDate = loginDate;
	}

	public String getEmailNew() {
		return emailNew;
	}

	public void setEmailNew(final String emailNew) {
		this.emailNew = emailNew;
	}

}

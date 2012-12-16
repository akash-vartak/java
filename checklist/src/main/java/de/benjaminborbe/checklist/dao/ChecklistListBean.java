package de.benjaminborbe.checklist.dao;

import java.util.Calendar;

import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.checklist.api.ChecklistList;
import de.benjaminborbe.checklist.api.ChecklistListIdentifier;
import de.benjaminborbe.storage.tools.Entity;
import de.benjaminborbe.storage.tools.HasCreated;
import de.benjaminborbe.storage.tools.HasModified;

public class ChecklistListBean implements Entity<ChecklistListIdentifier>, ChecklistList, HasCreated, HasModified {

	private static final long serialVersionUID = -8803301003126328406L;

	private ChecklistListIdentifier id;

	private String name;

	private Calendar created;

	private Calendar modified;

	private UserIdentifier owner;

	@Override
	public ChecklistListIdentifier getId() {
		return id;
	}

	@Override
	public void setId(final ChecklistListIdentifier id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
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
	public Calendar getModified() {
		return modified;
	}

	@Override
	public void setModified(final Calendar modified) {
		this.modified = modified;
	}

	@Override
	public UserIdentifier getOwner() {
		return owner;
	}

	public void setOwner(final UserIdentifier owner) {
		this.owner = owner;
	}

}

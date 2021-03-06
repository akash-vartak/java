package de.benjaminborbe.projectile.util;

import de.benjaminborbe.projectile.dao.ProjectileReportIdentifier;
import de.benjaminborbe.tools.mapper.Mapper;

public class MapperProjectileReportIdentifier implements Mapper<ProjectileReportIdentifier> {

	@Override
	public String toString(final ProjectileReportIdentifier value) {
		return value != null ? value.getId() : null;
	}

	@Override
	public ProjectileReportIdentifier fromString(final String value) {
		return value != null ? new ProjectileReportIdentifier(value) : null;
	}

}

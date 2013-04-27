package de.benjaminborbe.gallery.dao;

import com.google.inject.Provider;
import de.benjaminborbe.gallery.api.GalleryGroupIdentifier;
import de.benjaminborbe.gallery.util.MapperGalleryGroupIdentifier;
import de.benjaminborbe.tools.mapper.MapperBoolean;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Singleton
public class GalleryGroupBeanMapper extends MapObjectMapperAdapter<GalleryGroupBean> {

	@Inject
	public GalleryGroupBeanMapper(
		final Provider<GalleryGroupBean> provider,
		final MapperGalleryGroupIdentifier mapperGalleryGroupIdentifier,
		final MapperString mapperString,
		final MapperBoolean mapperBoolean,
		final MapperCalendar mapperCalendar
	) {
		super(provider, buildMappings(mapperGalleryGroupIdentifier, mapperString, mapperBoolean, mapperCalendar));
	}

	private static Collection<StringObjectMapper<GalleryGroupBean>> buildMappings(
		final MapperGalleryGroupIdentifier mapperGalleryGroupIdentifier, final MapperString mapperString,
		final MapperBoolean mapperBoolean, final MapperCalendar mapperCalendar
	) {
		final List<StringObjectMapper<GalleryGroupBean>> result = new ArrayList<>();
		result.add(new StringObjectMapperAdapter<GalleryGroupBean, GalleryGroupIdentifier>("id", mapperGalleryGroupIdentifier));
		result.add(new StringObjectMapperAdapter<GalleryGroupBean, String>("name", mapperString));
		result.add(new StringObjectMapperAdapter<GalleryGroupBean, Boolean>("shared", mapperBoolean));
		result.add(new StringObjectMapperAdapter<GalleryGroupBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<GalleryGroupBean, Calendar>("modified", mapperCalendar));
		return result;
	}
}

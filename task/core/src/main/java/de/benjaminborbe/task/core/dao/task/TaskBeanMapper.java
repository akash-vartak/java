package de.benjaminborbe.task.core.dao.task;

import com.google.inject.Provider;
import de.benjaminborbe.authentication.api.UserIdentifier;
import de.benjaminborbe.task.api.TaskContextIdentifier;
import de.benjaminborbe.task.api.TaskFocus;
import de.benjaminborbe.task.api.TaskIdentifier;
import de.benjaminborbe.task.core.util.MapperTaskContextIdentifier;
import de.benjaminborbe.task.core.util.MapperTaskFocus;
import de.benjaminborbe.task.core.util.MapperTaskIdentifier;
import de.benjaminborbe.task.core.util.MapperUserIdentifier;
import de.benjaminborbe.tools.mapper.MapperBoolean;
import de.benjaminborbe.tools.mapper.MapperCalendar;
import de.benjaminborbe.tools.mapper.MapperInteger;
import de.benjaminborbe.tools.mapper.MapperLong;
import de.benjaminborbe.tools.mapper.MapperString;
import de.benjaminborbe.tools.mapper.mapobject.MapObjectMapperAdapter;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapper;
import de.benjaminborbe.tools.mapper.stringobject.StringObjectMapperAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public class TaskBeanMapper extends MapObjectMapperAdapter<TaskBean> {

	public static final String FOCUS = "focus";

	public static final String PARENT_ID = "parentId";

	public static final String COMPLETED = "completed";

	public static final String OWNER = "owner";

	public static final String CONTEXT = "context";

	@Inject
	public TaskBeanMapper(
		final Provider<TaskBean> provider,
		final MapperTaskIdentifier mapperTaskIdentifier,
		final MapperTaskContextIdentifier mapperTaskContextIdentifier,
		final MapperString mapperString,
		final MapperUserIdentifier mapperUserIdentifier,
		final MapperLong mapperLong,
		final MapperBoolean mapperBoolean,
		final MapperCalendar mapperCalendar,
		final MapperInteger mapperInteger,
		final MapperTaskFocus mapperTaskFocus
	) {
		super(provider, buildMappings(mapperTaskIdentifier, mapperString, mapperUserIdentifier, mapperLong, mapperBoolean, mapperCalendar, mapperInteger, mapperTaskFocus,
			mapperTaskContextIdentifier));
	}

	private static Collection<StringObjectMapper<TaskBean>> buildMappings(
		final MapperTaskIdentifier mapperTaskIdentifier, final MapperString mapperString,
		final MapperUserIdentifier mapperUserIdentifier, final MapperLong mapperLong, final MapperBoolean mapperBoolean, final MapperCalendar mapperCalendar,
		final MapperInteger mapperInteger, final MapperTaskFocus mapperTaskFocus, final MapperTaskContextIdentifier mapperTaskContextIdentifier
	) {
		final List<StringObjectMapper<TaskBean>> result = new ArrayList<StringObjectMapper<TaskBean>>();
		result.add(new StringObjectMapperAdapter<TaskBean, TaskIdentifier>("id", mapperTaskIdentifier));
		result.add(new StringObjectMapperAdapter<TaskBean, TaskIdentifier>(PARENT_ID, mapperTaskIdentifier));
		result.add(new StringObjectMapperAdapter<TaskBean, TaskContextIdentifier>(CONTEXT, mapperTaskContextIdentifier));
		result.add(new StringObjectMapperAdapter<TaskBean, String>("name", mapperString));
		result.add(new StringObjectMapperAdapter<TaskBean, String>("description", mapperString));
		result.add(new StringObjectMapperAdapter<TaskBean, UserIdentifier>(OWNER, mapperUserIdentifier));
		result.add(new StringObjectMapperAdapter<TaskBean, Long>("duration", mapperLong));
		result.add(new StringObjectMapperAdapter<TaskBean, Boolean>(COMPLETED, mapperBoolean));
		result.add(new StringObjectMapperAdapter<TaskBean, Calendar>("completionDate", mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskBean, Calendar>("created", mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskBean, Calendar>("modified", mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskBean, Calendar>("start", mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskBean, Calendar>("due", mapperCalendar));
		result.add(new StringObjectMapperAdapter<TaskBean, Integer>("priority", mapperInteger));
		result.add(new StringObjectMapperAdapter<TaskBean, Long>("repeatStart", mapperLong));
		result.add(new StringObjectMapperAdapter<TaskBean, Long>("repeatDue", mapperLong));
		result.add(new StringObjectMapperAdapter<TaskBean, String>("url", mapperString));
		result.add(new StringObjectMapperAdapter<TaskBean, TaskFocus>(FOCUS, mapperTaskFocus));
		return result;
	}
}

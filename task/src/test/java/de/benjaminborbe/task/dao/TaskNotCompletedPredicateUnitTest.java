package de.benjaminborbe.task.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskNotCompletedPredicateUnitTest {

	@Test
	public void testApply() {
		final TaskNotCompletedPredicate p = new TaskNotCompletedPredicate();
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(null);
			assertTrue(p.apply(task));
		}
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(true);
			assertFalse(p.apply(task));
		}
		{
			final TaskBean task = new TaskBean();
			task.setCompleted(false);
			assertTrue(p.apply(task));
		}
	}
}
package de.benjaminborbe.lib.validation;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class ValidatorRegistryImpl implements ValidatorRegistry {

	private final Map<Class<?>, Set<Validator<?>>> data = new HashMap<Class<?>, Set<Validator<?>>>();

	@Inject
	public ValidatorRegistryImpl() {
	}

	@Override
	public Collection<Validator<?>> get(final Class<?> clazz) {
		if (data.containsKey(clazz)) {
			return data.get(clazz);
		} else {
			return new HashSet<Validator<?>>();
		}
	}

	@Override
	public void register(final Validator<?> validator) {
		final Set<Validator<?>> list;
		if (data.containsKey(validator.getType())) {
			list = data.get(validator.getType());
		} else {
			list = new HashSet<Validator<?>>();
			data.put(validator.getType(), list);
		}
		list.add(validator);
	}

}

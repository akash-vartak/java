package de.benjaminborbe.selenium.core.action;

import de.benjaminborbe.selenium.api.action.SeleniumActionConfiguration;
import de.benjaminborbe.tools.registry.Registry;

import javax.inject.Inject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class SeleniumActionRegistryImpl implements SeleniumActionRegistry, Registry<SeleniumAction> {

	private final Map<Class<? extends SeleniumActionConfiguration>, SeleniumAction> data = new HashMap<Class<? extends SeleniumActionConfiguration>, SeleniumAction>();

	@Inject
	public SeleniumActionRegistryImpl(
			final SeleniumActionSleep seleniumActionSleep,
			final SeleniumActionPageInfo seleniumActionPageInfo,
			final SeleniumActionGetUrl seleniumActionGetUrl,
			final SeleniumActionPageContent seleniumActionPageContent,
			final SeleniumActionClick seleniumActionClick,
			final SeleniumActionExpectText seleniumActionExpectText,
			final SeleniumActionExpectUrl seleniumActionExpectUrl,
			final SeleniumActionSendKeys seleniumActionSendKeys,
			final SeleniumActionSelect seleniumActionSelect,
			final SeleniumActionFollowAttribute seleniumActionFollowAttribute) {
		add(seleniumActionSelect);
		add(seleniumActionSendKeys);
		add(seleniumActionExpectUrl);
		add(seleniumActionSleep);
		add(seleniumActionPageInfo);
		add(seleniumActionGetUrl);
		add(seleniumActionPageContent);
		add(seleniumActionClick);
		add(seleniumActionExpectText);
		add(seleniumActionFollowAttribute);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SeleniumAction<SeleniumActionConfiguration> get(final Class<? extends SeleniumActionConfiguration> seleniumActionConfigurationClass) {
		return data.get(seleniumActionConfigurationClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(final SeleniumAction object) {
		final Class<? extends SeleniumActionConfiguration> type = object.getType();
		if (data.put(type, object) != null) {
			throw new SeleniumActionRegistryAlreadyRegisteredException("action for type " + type + " already registered");
		}
	}

	@Override
	public void add(final SeleniumAction... objects) {
		for (final SeleniumAction object : objects) {
			add(object);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(final SeleniumAction object) {
		final Class<? extends SeleniumActionConfiguration> type = object.getType();
		data.remove(type);
	}

	@Override
	public Collection<SeleniumAction> getAll() {
		return data.values();
	}
}

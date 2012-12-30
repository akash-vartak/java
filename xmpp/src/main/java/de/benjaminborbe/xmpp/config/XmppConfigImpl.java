package de.benjaminborbe.xmpp.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.configuration.api.ConfigurationDescription;
import de.benjaminborbe.configuration.api.ConfigurationService;
import de.benjaminborbe.configuration.tools.ConfigurationBase;
import de.benjaminborbe.configuration.tools.ConfigurationDescriptionInt;
import de.benjaminborbe.configuration.tools.ConfigurationDescriptionString;
import de.benjaminborbe.tools.util.ParseUtil;
import de.benjaminborbe.xmpp.XmppConstants;

@Singleton
public class XmppConfigImpl extends ConfigurationBase implements XmppConfig {

	private final ConfigurationDescriptionString username = new ConfigurationDescriptionString(null, XmppConstants.CONFIG_USERNAME, "Xmpp Username");

	private final ConfigurationDescriptionString password = new ConfigurationDescriptionString(null, XmppConstants.CONFIG_PASSWORD, "Xmpp Password");

	private final ConfigurationDescriptionString serverHost = new ConfigurationDescriptionString(null, XmppConstants.CONFIG_SERVERHOST, "Xmpp Host for Server");

	private final ConfigurationDescriptionInt serverPort = new ConfigurationDescriptionInt(5222, XmppConstants.CONFIG_SERVERPORT, "Xmpp Port for Server");

	@Inject
	public XmppConfigImpl(final Logger logger, final ConfigurationService configurationService, final ParseUtil parseUtil) {
		super(logger, configurationService, parseUtil);
	}

	@Override
	public String getUsername() {
		return getValueString(username);
	}

	@Override
	public String getPassword() {
		return getValueString(password);
	}

	@Override
	public String getServerHost() {
		return getValueString(serverHost);
	}

	@Override
	public Integer getServerPort() {
		return getValueInt(serverPort);
	}

	@Override
	public Collection<ConfigurationDescription> getConfigurations() {
		final Set<ConfigurationDescription> result = new HashSet<ConfigurationDescription>();
		result.add(username);
		result.add(password);
		result.add(serverHost);
		result.add(serverPort);
		return result;
	}
}

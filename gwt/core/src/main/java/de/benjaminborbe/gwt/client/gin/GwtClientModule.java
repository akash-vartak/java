package de.benjaminborbe.gwt.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import de.benjaminborbe.gwt.client.MainPanel;
import de.benjaminborbe.gwt.client.message.DefaultMessages;
import de.benjaminborbe.gwt.client.message.DefaultMessagesImpl;

import javax.inject.Singleton;

public class GwtClientModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MainPanel.class).in(Singleton.class);
		bind(DefaultMessages.class).to(DefaultMessagesImpl.class).in(Singleton.class);
	}

}

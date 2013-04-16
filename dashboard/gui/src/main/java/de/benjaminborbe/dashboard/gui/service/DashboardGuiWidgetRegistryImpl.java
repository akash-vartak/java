package de.benjaminborbe.dashboard.gui.service;

import com.google.inject.Singleton;

import de.benjaminborbe.dashboard.api.DashboardContentWidget;
import de.benjaminborbe.tools.registry.RegistryBase;

@Singleton
public class DashboardGuiWidgetRegistryImpl extends RegistryBase<DashboardContentWidget> implements DashboardGuiWidgetRegistry {

}
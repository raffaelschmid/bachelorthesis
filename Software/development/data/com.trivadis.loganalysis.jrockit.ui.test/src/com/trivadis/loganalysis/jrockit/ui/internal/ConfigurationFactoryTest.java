package com.trivadis.loganalysis.jrockit.ui.internal;

import static org.junit.Assert.assertNotNull;

import org.eclipse.ui.XMLMemento;
import org.junit.Before;
import org.junit.Test;

import com.trivadis.loganalysis.ui.domain.profile.IConfiguration;

public class ConfigurationFactoryTest {
	private final IConfigurationFactory instance = new ConfigurationFactory();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLoadConfigurationFrom_empty() {
		final IConfiguration loadConfigurationFrom = instance.loadConfigurationFrom(XMLMemento.createWriteRoot("empty"));
		assertNotNull(loadConfigurationFrom);
	}
	@Test
	public void testLoadConfigurationFrom_null() {
		final IConfiguration loadConfigurationFrom = instance.loadConfigurationFrom(null);
		assertNotNull(loadConfigurationFrom);
	}

}

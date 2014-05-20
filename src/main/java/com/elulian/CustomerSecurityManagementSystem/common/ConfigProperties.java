package com.elulian.CustomerSecurityManagementSystem.common;

@Deprecated
public interface ConfigProperties {

	// public static final
	// use getResourceAsStream
	String CONFIG_PROPERTY_FILE = "/config.properties";
	String BRANCH_PROPERTY_FILE = "/branch.properties";
//	use common configuration, not tested in war
	String LOCALE_PROPERTY_FILE = ConfigProperties.class.getResource("/locale.properties").getPath();
	String RULE_FILE = ConfigProperties.class.getResource("/thresholds.xml").getPath();
}

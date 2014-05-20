package com.elulian.CustomerSecurityManagementSystem.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class for Servlet: HelperServlet  
 *
 */
public class ConfigServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */



	private static final Logger logger = Logger.getLogger(ConfigServlet.class);

	private static String CONFIG_PROPERTY_FILE;
	private static String BRANCH_PROPERTY_FILE;
	private static String LOCALE_PROPERTY_FILE;
	private static String RULE_FILE;

	// Normal configuration, like: pageSize
	private static PropertiesConfiguration m_configProps;
	// Branch configuration, contains all branch names
	private static PropertiesConfiguration m_branchProps;

	private static PropertiesConfiguration m_localeProps;
	
	private static XMLConfiguration m_thresholdConfig;

	public ConfigServlet() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();

		//		use common configuration, not tested in war
		CONFIG_PROPERTY_FILE = ConfigServlet.class.getResource(
				"/config.properties").getPath();
		BRANCH_PROPERTY_FILE = ConfigServlet.class.getResource(
				"/branch.properties").getPath();
		LOCALE_PROPERTY_FILE = ConfigServlet.class.getResource(
				"/locale.properties").getPath();
		RULE_FILE = ConfigServlet.class.getResource("/thresholds.xml")
				.getPath();
		try {
//			read only properties, no thread safe issue
			// retrieve data the properties files
			m_configProps = new PropertiesConfiguration(CONFIG_PROPERTY_FILE);
			m_configProps
					.setReloadingStrategy(new FileChangedReloadingStrategy());
			// ManagedReloadingStrategy is an alternative to automatic
			// reloading. It allows to hot-reload properties on a running
			// application but only when requested by admin. The refresh()
			// method will force a reload of the configuration source.
			m_branchProps = new PropertiesConfiguration(BRANCH_PROPERTY_FILE);
			m_branchProps
					.setReloadingStrategy(new FileChangedReloadingStrategy());
			m_localeProps = new PropertiesConfiguration(LOCALE_PROPERTY_FILE);
			m_localeProps
					.setReloadingStrategy(new FileChangedReloadingStrategy());
			m_thresholdConfig = new XMLConfiguration(RULE_FILE);
			
			// load branch list in application
			Map<String,String> map = new HashMap<String,String>(); 
			for(String branch :Config.getInstance().listAllBranches()){
				map.put(branch, branch);
			}
			if(map.isEmpty())
				throw new ServletException("Can't retrieve branch list");
			getServletContext().setAttribute("branchList", map);
		} catch (ConfigurationException e) {
			logger.error("Config.java********* Can't find Config file", e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public static PropertiesConfiguration getLocaleProps() {
		return m_localeProps;
	}

	public static PropertiesConfiguration getConfigProps() {
		return m_configProps;
	}

	public static PropertiesConfiguration getBranchProps() {
		return m_branchProps;
	}
	
	public static XMLConfiguration getThresholdConfig() {
		return m_thresholdConfig;
	}

}
package com.elulian.CustomerSecurityManagementSystem.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @see ConfigServlet
 * @deprecated
 * @author cloudlu
 *
 */

@Deprecated
public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);

	private static Utility m_instance;

	// prevent muliti create utitlity
	// Double-Checked Locking to improve performance
	// still not safe, see http://blog.csdn.net/cloud_ll/archive/2009/04/24/4105623.aspx
	public static Utility getInstance() {
		if (m_instance == null) {
			synchronized (Utility.class) {
				if (m_instance == null)
					m_instance = new Utility();
			}
		}
		return m_instance;
	}

	private long m_configLastModifiedTime = 0;
	private long m_branchLastModifiedTime = 0;
	private File m_configFile;
	private File m_branchFile;
	private Properties m_configProps;
	private Properties m_branchProps;

	private Utility() {
		m_configProps = new Properties();
		m_branchProps = new Properties();
		m_configFile = new File(ConfigProperties.CONFIG_PROPERTY_FILE);
		m_configLastModifiedTime = m_configFile.lastModified();
		m_branchFile = new File(ConfigProperties.BRANCH_PROPERTY_FILE);
		m_branchLastModifiedTime = m_branchFile.lastModified();
		loadProperties(m_configProps, ConfigProperties.CONFIG_PROPERTY_FILE);
		loadProperties(m_branchProps, ConfigProperties.BRANCH_PROPERTY_FILE);
	}

	/**
	 * 
	 * @param props Properties to retrieve the items in config file 
	 * @param path config file path
	 * @param lastModifiedTime the config file's last modify time keep in application
	 * @return the file's last modified time, if file is not exist or modified time less than lastModifiedTime, return lastModifiedTime.
	 */
	private long loadProperties(Properties props, String path,
			long lastModifiedTime) {
		File file = new File(Utility.class.getResource(path).getPath());
		long newTime = file.lastModified();
		if (newTime == 0) {
			if (lastModifiedTime == 0) {
				logger.info("Can't find file: " + Utility.class.getResource(path).getPath());
			} else {
				logger.error("file was deleted: " + Utility.class.getResource(path).getPath());
			}
		} else if (newTime > lastModifiedTime) {
			if (loadProperties(props, path)) {
				return newTime;
			}
		}
		return lastModifiedTime;
	}

	/**
	 * 
	 * @param props Properties to retrieve the items in config file 
	 * @param path onfig file path
	 * @return true: load successful, false: load filed, see log in log file.
	 */
	private boolean loadProperties(Properties props, String path) {
		try {
			InputStream in = Utility.class.getResourceAsStream(path);
			props.clear();
			props.load(in);
			in.close();
			return true;
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return false;
	}

	public String getMessageByBundle(String key) {
		synchronized (this) {
			m_configLastModifiedTime = loadProperties(m_configProps,
					ConfigProperties.CONFIG_PROPERTY_FILE, m_configLastModifiedTime);
		}
		String val = m_configProps.getProperty(key);
		if (val == null) {
			// return default page size: 5
			return "5";
		}

		return val;
	}

	public String getBranchByKey(int key) {
		//prevent muliti change to m_branchLastModifiedTime and m_branchProps
		synchronized (this) {
			m_branchLastModifiedTime = loadProperties(m_branchProps,
					ConfigProperties.BRANCH_PROPERTY_FILE, m_branchLastModifiedTime);
		}
		String val = m_branchProps.getProperty(key + "");
		if (val == null) {
			// default branch is ALL
			return "ALL";
		}
		return val;
	}

	@SuppressWarnings("unchecked")
	public Map listAllBranches() {
		synchronized (this) {
			m_branchLastModifiedTime = loadProperties(m_branchProps,
					ConfigProperties.BRANCH_PROPERTY_FILE, m_branchLastModifiedTime);
		}
		return new TreeMap(m_branchProps);
	}
}

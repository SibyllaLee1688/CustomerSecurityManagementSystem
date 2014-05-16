package com.elulian.CustomerSecurityManagementSystem.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Get the page size and branch
 * @author cloudlu
 *
 */
public class Config {

//	private static Logger logger = Logger.getLogger(Config.class);

	private PropertiesConfiguration m_configProps;
	private PropertiesConfiguration m_branchProps;

	private static Config m_instance;

	public static Config getInstance() {
		if (m_instance == null) {
			synchronized (Config.class) {
				if (m_instance == null)
					m_instance = new Config();
			}
		}
		return m_instance;
	}

	private Config() {
		m_configProps = ConfigServlet.getConfigProps();
		m_branchProps = ConfigServlet.getBranchProps();

	}

	public int getPageSizeByKey(String key) {
		if (m_configProps != null) {
			return m_configProps.getInt(key, 5);
		}
		return 5;
	}

	public String getBranchByKey(int key) {
		if (m_branchProps != null) {
			// default branch is ALL
			return m_branchProps.getString(key + "", "ALL");
		}
		return "ALL";
	}

	@SuppressWarnings("unchecked")
	public List<String> listAllBranches() {
		List<String> branches = new ArrayList<String>();
		if (m_branchProps != null) {
			Iterator<String> i = m_branchProps.getKeys();
//			String key;
			while (i.hasNext()) {
				branches.add(m_branchProps.getString(i.next()));
			}
		}
		return branches;
	}

}

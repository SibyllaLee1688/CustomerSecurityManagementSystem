package com.elulian.CustomerSecurityManagementSystem.service;

import javax.annotation.security.RolesAllowed;

import com.elulian.CustomerSecurityManagementSystem.exception.NotExistsException;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

public interface IThresholdService{
	
	/* retrieve from database later */
	static final String defaultGroupId = "com.elulian";
	static final String defaultArtifactId = "CustomerSecurityManagementRules";
	static final String defaultVersion = "1.0";
	

	@Deprecated
	void edit(Threshold threshold);

	@Deprecated
	void setMatchThresholdsInfo(CustomerInfo info);
	/**
	 * init the engine to start the service
	 * @param groupId TODO
	 * @param artifactId TODO
	 * @param version TODO
	 * @throws NotExistsException fail to find the threshold rule kmodule from maven
	 */
	public void initThresholdService(String groupId, String artifactId, String version) throws NotExistsException;

	/**
	 * refresh the engine rules with new rule sets,
	 * rule sets comes from maven repo
	 * @param groupId 
	 * @param artifactId 
	 * @param version 
	 * @throws NotExistsException fail to find the threshold rule kmodule from maven
	 */
	public void refreshThresholdService(String groupId, String artifactId, String version) throws NotExistsException;

	/**
	 * execute engine rules to calculate thresholds
	 * 
	 * @param info
	 *            CustomerInfo to calculate
	 */
	void setCustomerThresholdsInfo(CustomerInfo info);
	
}

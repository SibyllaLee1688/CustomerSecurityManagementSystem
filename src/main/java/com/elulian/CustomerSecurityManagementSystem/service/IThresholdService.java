package com.elulian.CustomerSecurityManagementSystem.service;

import javax.annotation.security.RolesAllowed;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

public interface IThresholdService{

	@Deprecated
	void edit(Threshold threshold);

	@Deprecated
	void setMatchThresholdsInfo(CustomerInfo info);
	/**
	 * init the engine to start the service
	 */
	public void initThresholdService();

	/**
	 * refresh the engine rules
	 */
	public void refreshThresholdService();

	/**
	 * execute engine rules to calculate thresholds
	 * 
	 * @param info
	 *            CustomerInfo to calculate
	 */
	void setCustomerThresholdsInfo(CustomerInfo info);
	
	/**
	 * edit the thresholdRules
	 * 
	 * @return return true if rule edit sucess, else return false
	 */
	boolean editThresholdRulesFile();
	
	/**
	 * validate the threshold rule file
	 * 
	 * @return return true if rules are syntax ok, else return false
	 */
	boolean validateThresholdRulesFile();
}

package com.elulian.CustomerSecurityManagementSystem.service;

import javax.annotation.security.RolesAllowed;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

public interface IThresholdService extends IBaseService<Threshold, Integer>{

	@Deprecated
	void edit(Threshold threshold);

	@Deprecated
	void setMatchThresholdsInfo(CustomerInfo info);
	
}

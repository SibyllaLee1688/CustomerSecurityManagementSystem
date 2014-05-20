package com.elulian.CustomerSecurityManagementSystem.service;

import java.util.List;

import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

public interface ICustomerInfoService extends IBaseService<CustomerInfo, Integer>{

	List<CustomerInfo> getCustomerInfosByCondition(Condition condition);

	
	public long getTotalCount(Condition condition);


	void setMatchThresholdsInfo(CustomerInfo info);
}

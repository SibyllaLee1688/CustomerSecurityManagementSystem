package com.elulian.CustomerSecurityManagementSystem.dao;

import java.util.List;

import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

public interface ICustomerInfoDAO extends IBaseDAO<CustomerInfo, Integer> {

	List<CustomerInfo> getCustomerInfoByCondition(Condition condition);

	long getTotalCount(Condition condition);

}

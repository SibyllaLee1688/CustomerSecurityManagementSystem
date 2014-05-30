package com.elulian.CustomerSecurityManagementSystem.dao;

import java.util.List;

import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

public interface IUserInfoDAO extends IBaseDAO<UserInfo, Integer> {
	
	/**
	 * 
	 * @param name
	 * @return null if name is not exists, else return userInfo
	 */
	public UserInfo getUserByName(String name);

	public long getTotalCount(Condition condition);

	public List<UserInfo> getUserInfoByCondition(Condition condition);
}

package com.elulian.CustomerSecurityManagementSystem.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;


import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.UserExistsException;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

//@RolesAllowed({"ROLE_USER"})
public interface IUserInfoService extends IBaseService<UserInfo, Integer>{
	/*public List<UserInfo> findAll();
	
	public UserInfo findById(Integer id);
	
	public UserInfo save(UserInfo userInfo);
	
	public void remove(Integer id);
	
	public void remove(UserInfo userInfo);*/

	public long getTotalCount(Condition condition);
	
	public List<UserInfo> getUserInfosByCondition(Condition condition);
	
	public UserInfo getUserInfoByName(String name);
	
	public boolean changeUserPassword(String username, String oldPassword, String newPassword);
	
}

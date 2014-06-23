package com.elulian.CustomerSecurityManagementSystem.service.impl;


import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.dao.IUserInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.service.IUserInfoService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

@Service("userInfoService")
public class UserInfoService extends BaseService<UserInfo, Integer> implements
		IUserInfoService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
    private PasswordEncoder passwordEncoder;	
	
	private IUserInfoDAO userInfoDAO;

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

    @Autowired
    public void setUserInfoDAO(IUserInfoDAO userInfoDAO) {
        this.dao = userInfoDAO;
        this.userInfoDAO = userInfoDAO;
    }
    
	/*
	 * protected IUserInfoDAO getUserInfoDAO() { if (userInfoDAO == null) {
	 * userInfoDAO = DAOFactory.getDAOFacotry().getIUserInfoDAO(); } return
	 * userInfoDAO; }
	 */

	@Override
	@RolesAllowed({"ROLE_ADMIN"})
	public long getTotalCount(Condition condition) {
		return userInfoDAO.getTotalCount(condition);
	}

	@Override
	@RolesAllowed({"ROLE_ADMIN"})
	public List<UserInfo> getUserInfosByCondition(Condition condition) {
		return userInfoDAO.getUserInfoByCondition(condition);
	}

	@Override
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public UserInfo getUserInfoByName(String name) {
		return userInfoDAO.getUserByName(name);
	}

	/*@Override
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public UserInfo getUserInfoByUserId(String userId) {
		return userInfoDAO.getUserInfoByUserId(userId);
	}*/

	@Override
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	//@Transactional
	public UserInfo save(UserInfo userInfo) { //throws ExistsException, DataMissingException{
		
		if(null == userInfo.getId()){
			/* new user, need to encode password */
			encodeUserPassword(userInfo);
		}
		
		/*
		 * before save userinfo in userinforservice +++++++++++++++++++++++++++++++
		3724  mysql  TRACE  [main] openjpa.jdbc.SQL - <t 327916146, conn 1227641731> [1 ms] spent
		after save userinfo in userinforservice +++++++++++++++++++++++++++++++
		Exception-=============------------class org.springframework.dao.InvalidDataAccessApiUsageException
		exception happens after the save function call, seems the commit action controlled by transactional
		happens after ths save method, so try to catch exception inside the save functio is not reasonable.
		If we need to do this, we have to make sure the save function in dao layer happens during the function
		call. To make this happen, call flush function inside dao save function.
		 */
			
		return  super.save(userInfo);
		
		/* } catch (ExistsException e){
			 throw new UserExistsException(userInfo.getUsername() + "or" + userInfo.getEmail() + "exists", e);
		 }*/
	}

	private void encodeUserPassword(UserInfo userInfo) {
		//logger.debug("before encode: " + userInfo.getPassword());
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		//logger.debug("after encode: " + userInfo.getPassword());
	}

	@Override
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public boolean changeUserPassword(String username, String oldPassword,
			String newPassword) {
		
		UserInfo userInfo = getUserInfoByName(username);
		
		if(null == userInfo){
			logger.error(username + "not exists");
			return false;
		}
		
		if(passwordEncoder.matches(oldPassword, userInfo.getPassword())){
			userInfo.setPassword(newPassword);
			encodeUserPassword(userInfo);
			save(userInfo);
			logger.debug("valid password for user: " + username);
			return true;
		}
		
		logger.warn("invalid password for user: " + username);
		
		return false;
		
	}

	@Override
	@Transactional
	/* transactional is required due to aop doesn't cover this method
	 * as it is not belongs to service interfaces
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if(null == username || 0 == username.trim().length())
			throw new UsernameNotFoundException("wrong username: " + username);	
		UserInfo userInfo = userInfoDAO.getUserByName(username);	
		if(null != userInfo)
			return userInfo;
		throw new UsernameNotFoundException("wrong username: " + username);
	}
	
}

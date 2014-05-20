package com.elulian.CustomerSecurityManagementSystem.web.selfService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.UserExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IUserInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("SelfServiceAction") @Scope("prototype")
public class SelfServiceAction extends ActionSupport {
	
	private String username;   //--------property we need to use in the pages
	private String password;
	private String newPassword;
	private String reNewPassword;
    private UserInfo userInfo;

    @Autowired
	private IUserInfoService userInfoService;
	
   	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	/*private IUserInfoService getService(){
		if(service == null)
			service = ServiceFactory.getServiceFactory().getIUserInfoService();
		return service;
	}*/
	
	/* replaced by spring security */
	
/*	public String logout(){
		ActionContext ctx = ActionContext.getContext();
		Map<String, Object> session = ctx.getSession();
		session.remove("userId");
		session.remove("branch");
		return SUCCESS;
	}
	
	public String login() {
   		//addActionError,addActionMessage
		 
		
		UserInfo user = userInfoService.getUserInfoByUserId(userId);
			if(user != null && user.getPassword().equals(password)){
				ActionContext ctx = ActionContext.getContext();
				Map<String, Object> session = ctx.getSession();
				session.put("userId", user.getUserId());
				session.put("branch", user.getBranch());
				//session.put("branch", Utility.getBranchByKey(user.getBranch()));
//				session.
				return SUCCESS;
			}
		
			addActionError("Sorry, we meet unexpect database error, please contact our support.");
			return ERROR;
			
			addActionError(getText("login.failed"));
			return ERROR;
    }*/
	
	public String start(){
		return SUCCESS;
	}
	
	
	public String register(){
		return ERROR;
	}

	public String changePassword() {
		/*
		 * UserInfo user = userInfoService.getUserInfoByName(username); if(null
		 * != user &&
		 * userInfoService.isValidUserPassword(user.getUsername(),password,
		 * newPas)){ user.setPassword(newPassword);
		 */

		if(!newPassword.equals(reNewPassword)){
			addActionError(getText("login.failed"));
			return INPUT;
		}
		
		try {
			if (true == userInfoService.changeUserPassword(username, password,
					newPassword)) {
/*				SecurityContextHolder.clearContext();
 * 				getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,newPasswowrd);
 */
				ActionContext.getContext().put("tip",
						getText("user.changePasswordSuccess"));
				return SUCCESS;
			}
		} catch (DataIntegrityViolationException e) {
			LOG.error("unexpected exception, Exist user", e.getMessage(),
					username);
		} catch (InvalidDataAccessApiUsageException e) {
			LOG.error("unexpected exception, Incomplicated user information",
					e.getMessage());
		}
		addActionError(getText("user.inconsistentNewPassword"));
		return INPUT;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}

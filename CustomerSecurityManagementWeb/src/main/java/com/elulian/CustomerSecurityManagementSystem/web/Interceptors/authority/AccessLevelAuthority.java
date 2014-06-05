package com.elulian.CustomerSecurityManagementSystem.web.Interceptors.authority;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.elulian.CustomerSecurityManagementSystem.service.IUserInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessLevelAuthority extends AbstractInterceptor{
	
	
	private static Logger logger = LoggerFactory.getLogger(AccessLevelAuthority.class);
	
	@Autowired
	private IUserInfoService userInfoService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		 
		ActionContext ctx = invocation.getInvocationContext(); 
		Map<String,Object> session = ctx.getSession(); 
		 
		String username = (String)session.get("username");
		/*IUserInfoService service = ServiceFactory.getServiceFactory().getIUserInfoService();*/
		UserInfo user = userInfoService.getUserInfoByName(username);
		
		if (user != null && user.getBranch().equalsIgnoreCase("ALL")) { 
			return invocation.invoke(); 
		} else { 
		 
			logger.debug("No permission in AccessLevelAuthority");
			ctx.put("tip" , "You don't have permission to access this page"); 
		 
			return Action.LOGIN; 
		} 
	}
}

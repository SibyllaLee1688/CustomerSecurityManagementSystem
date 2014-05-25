package com.elulian.CustomerSecurityManagementSystem.web.Interceptors.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
@Deprecated
public class AjaxSessionCheck extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();
		
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		
		// Ajax request
		if(request.getHeader("x-requested-with")!=null      
			    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){     
			Map<String,Object> session = ctx.getSession();
			String user = (String)session.get("userId");     
			// Session timeout
			if (user == null) { 
				HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
				response.setHeader("sessionstatus","timeout"); 
			}
		} 
		return invocation.invoke();
	}
}

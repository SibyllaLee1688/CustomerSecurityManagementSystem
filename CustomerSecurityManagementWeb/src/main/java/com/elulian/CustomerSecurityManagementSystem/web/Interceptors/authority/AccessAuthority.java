package com.elulian.CustomerSecurityManagementSystem.web.Interceptors.authority;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessAuthority extends AbstractInterceptor {

	private static Logger logger = LoggerFactory.getLogger(AccessAuthority.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//ȡ��������ص�ActionContextʵ�� 
		ActionContext ctx = invocation.getInvocationContext(); 
		Map<String,Object> session = ctx.getSession(); 
		//ȡ����Ϊuser��Session���� 
		String user = (String)session.get("userId"); 
		//����Ѿ���½��������� 
		if (user != null) { 
			return invocation.invoke(); 
		} else { 
			//û�е�½������������ʾ���ó�һ��HttpServletRequest����
			logger.debug("No permission in AccessAuthority");
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))){     
					HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
					response.setHeader("sessionstatus","timeout"); 
				}
			}
			ctx.put("tip" , "You are not login yet, please use your id to login"); 
			//ֱ�ӷ���login���߼���ͼ 
			return Action.LOGIN;
	}
}

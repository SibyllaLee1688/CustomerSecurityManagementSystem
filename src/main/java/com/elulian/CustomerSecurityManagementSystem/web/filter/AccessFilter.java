package com.elulian.CustomerSecurityManagementSystem.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AccessFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(AccessFilter.class);

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") != null) {
			chain.doFilter(request, response);
		} else {
//			req.setAttribute("tip", "You are not login yet, please use your id to login");
			logger.debug("No permission in AccessFilter");
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
		}
		
	}
}
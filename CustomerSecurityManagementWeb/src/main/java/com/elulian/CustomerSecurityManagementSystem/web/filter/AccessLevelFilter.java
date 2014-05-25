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

public class AccessLevelFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(AccessLevelFilter.class);

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		if (session.getAttribute("userId") != null
				&& "ALL".equalsIgnoreCase((String)session.getAttribute("branch"))) {
			chain.doFilter(request, response);
		} else {
//			req.setAttribute("tip", "You don't have permission to access this page");
			logger.debug("No permission in AccessLevelFilter");
			res.sendRedirect(req.getContextPath() + "/login/login.jsp");
		}
	}

}
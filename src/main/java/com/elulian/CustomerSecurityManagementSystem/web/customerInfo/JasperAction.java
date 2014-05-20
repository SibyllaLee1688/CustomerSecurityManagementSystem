package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

//imports
import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.elulian.CustomerSecurityManagementSystem.service.ICustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.impl.CustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

import com.opensymphony.xwork2.ActionSupport;

@Controller("jasperAction") @Scope("prototype")
public class JasperAction extends ActionSupport implements SessionAware {
	
	private List<CustomerInfo> customerInfos;
	private Condition condition;
	
	@Autowired
	private ICustomerInfoService customerInfoService;
	private Map<String, Object> sessionMap;

	public JasperAction() {
		/*if (customerInfoService == null)
			customerInfoService = new CustomerInfoService();*/
	}

	public String execute() throws Exception {

		// get data from database
		if (condition == null) {
			condition = new Condition();
		}
		// get user branch
		String branch = (String) sessionMap.get("branch");
		// set user branch if user branch is not ALL
		// normal user can only see the customerInfos in his/her branch
		if (branch != null && (!branch.equalsIgnoreCase("ALL"))) {
			condition.setSearchBranch(branch);
		}
		condition.setSortBy("branch");
		customerInfos = customerInfoService.getCustomerInfosByCondition(condition);
		/*
		 * Here we compile our xml jasper template to a jasper file. Note: this
		 * isn't exactly considered 'good practice'. You should either use
		 * precompiled jasper files (.jasper) or provide some kind of check to
		 * make sure you're not compiling the file on every request. If you
		 * don't have to compile the report, you just setup your data source
		 * (eg. a List)
		 */
		try {
			String reportSource = ServletActionContext.getServletContext()
					.getRealPath("/customerInfo/jasper_template.jrxml");
			if (reportSource == null)
				ServletActionContext.getServletContext().getResource(
						"/customerInfo/jasper_template.jrxml").getPath();
			File parent = new File(reportSource).getParentFile();
			JasperCompileManager.compileReportToFile(reportSource, new File(
					parent, "compiled_jasper_template.jasper")
					.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	public List<CustomerInfo> getCustomerInfos() {
		return customerInfos;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
}
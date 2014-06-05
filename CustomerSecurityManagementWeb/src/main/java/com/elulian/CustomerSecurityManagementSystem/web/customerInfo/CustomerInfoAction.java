package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.elulian.CustomerSecurityManagementSystem.common.Config;
import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.ExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.ICustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.service.impl.CustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

@Controller("customerInfoAction") @Scope("prototype")
public class CustomerInfoAction extends ActionSupport implements
		ParameterAware, SessionAware {

	private static Logger logger = LoggerFactory.getLogger(CustomerInfoAction.class);

	@Autowired
	private ICustomerInfoService customerInfoService;
	private List<CustomerInfo> customerInfos;
	private CustomerInfo customerInfo;
	private Integer id;
	private Condition condition;
	private Map<String, String[]> params;
	private Map<String, Object> sessionMap;

	@Deprecated
	private boolean hasNext = false;
	@Deprecated
	private boolean hasPrevious = false;
	@Deprecated
	private int currentPage = 1;
	@Deprecated
	private long totalPage;
	@Deprecated
	private int pageSize = Config.getInstance().getPageSizeByKey("pageSize");
	private long totalCount = 0;

	/*
	 * JSON return value for EXT form submission (save/delete action). EXT
	 * require response: would process the following server response for a
	 * successful submission: { success: true, msg: 'Consignment updated' } and
	 * the following server response for a failed submission: { success: false,
	 * msg: 'You do not have permission to perform this operation' }
	 */
	private boolean actionResult;
	private String actionMessage;

	public CustomerInfoAction() {
		/*if (customerInfoService == null)
			customerInfoService = new CustomerInfoService();*/
	}

	@JSON(name = "success")
	public boolean getActionResult() {
		return actionResult;
	}

	@JSON(name = "msg")
	public String getActionMessage() {
		return actionMessage;
	}
	
	@JSON(name = "customerInfos")
	public List<CustomerInfo> getCustomerInfos() {
		return customerInfos;
	}

	@JSON(name = "totalCount")
	public long getTotalCount() {
		return totalCount;
	}

	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public Condition getCondition() {
		return condition;
	}
	@Deprecated
	public boolean isHasNext() {
		return hasNext;
	}
	@Deprecated
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	@Deprecated
	public long getTotalPage() {
		return totalPage;
	}
	@Deprecated
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Deprecated
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Deprecated
	public String list() {
		setInfos();
		return Action.SUCCESS;
	}

	@Deprecated
	/**
	 * remain for condition constructor, should be removed later, construct condition in the client side instead of server side
	 */
	private void setInfos() {
		if (condition == null) {
			// access without condtion
			logger
					.debug("First time to access customerInfoAction, create a condition object");
			condition = new Condition();
		} else {
			if (condition.getCustomerId() != null
					&& condition.getCustomerId().length() == 0)
				condition.setCustomerId(null);
			if (condition.getSearchBranch() != null
					&& condition.getSearchBranch().length() == 0)
				condition.setSearchBranch(null);
		}
		// get user branch
		String branch = (String) sessionMap.get("branch");
		// set user branch if user branch is not ALL
		// normal user can only see the customerInfos in his/her branch
		logger.debug(branch + "***********************");
		if (branch != null && (!branch.equalsIgnoreCase("ALL"))) {
			condition.setSearchBranch(branch);
		}
		long total = customerInfoService.getTotalCount(condition);
		totalPage = (total % pageSize == 0) ? (total / pageSize) : (total
				/ pageSize + 1);
		if (totalPage == 0)
			totalPage = 1;
		// set currentPage if pageNum is exist.
		if (params.get("pageNum") != null) {
			int pageNum = Integer.valueOf((params.get("pageNum"))[0]);
			logger.debug(logger.getName() + pageNum);
			if (pageNum >= 1 && pageNum <= totalPage) {
				currentPage = pageNum;
			}
		}

		condition.setStartRow((currentPage - 1) * pageSize);
		condition.setMaxRow(pageSize);

		hasNext = (currentPage < totalPage) ? true : false;
		hasPrevious = (currentPage <= 1) ? false : true;

		this.customerInfos = customerInfoService.getCustomerInfosByCondition(condition);
	}

	/**
	 * Replaced by extPaging
	 */
	@Deprecated
	public String search() {
		setInfos();
		return Action.SUCCESS;
	}

	public void prepare() throws Exception {
		if (id != null)
			customerInfo = customerInfoService.findById(id);
	}

	public String detailInfo() {
		if (customerInfo == null && id != null)
			customerInfo = customerInfoService.findById(id);
		return SUCCESS;
	}

	/**
	 * Update CustomerInfo
	 * 
	 * @return
	 */
	public String save() {
		if(logger.isDebugEnabled()){
			logger.debug("******************** save");
			try{
				logger.debug(params.get("customerInfo.sex")[0]);
				logger.debug(new String(params.get("customerInfo.sex")[0].getBytes("UTF-8")));
			} catch (Exception e){
				
			}
		}
			
		if (customerInfo != null) {
			try {
				customerInfoService.save(customerInfo);
			} catch (DataIntegrityViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataAccessApiUsageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actionResult = true;
			actionMessage = "CustomerInfo Update successful!";
			return SUCCESS;
		}
		actionResult = false;
		actionMessage = "CustomerInfo is null!";
		return ERROR;
	}

	/**
	 * delete customerInfo by ID
	 * 
	 * @return
	 */
	public String delete() {
		if (id != null) {
			customerInfoService.remove(id);
			id = null;
			actionResult = true;
			actionMessage = "CustomerInfo delete successful!";
			return SUCCESS;
		}
		actionResult = false;
		actionMessage = "CustomerInfo is null!";
		return ERROR;
	}

	public String processCustomerRiskInfo() {

		Condition con = new Condition();

		long startTime = System.currentTimeMillis();
		long count = customerInfoService.getTotalCount();
		List<CustomerInfo> list = null;
		String temp = null;
		// should replace with a list of thresholds with execute method
		for (int i = 0; i <= count; i = i + 100) {

			con.setStartRow(i);
			con.setMaxRow(100);
			list = customerInfoService.getCustomerInfosByCondition(con);
			for (CustomerInfo info : list) {
				customerInfoService.setMatchThresholdsInfo(info);
				customerInfoService.save(info);
			}
		}
		long endTime = System.currentTimeMillis();
		logger.info("Disposal time: " + (endTime - startTime));
		return SUCCESS;
	}
	
	public String extPaging() {
		if (condition == null) {
			condition = new Condition();
		} else {
			if (condition.getCustomerId() != null
					&& condition.getCustomerId().length() == 0)
				condition.setCustomerId(null);
			if (condition.getSearchBranch() != null
					&& condition.getSearchBranch().length() == 0)
				condition.setSearchBranch(null);
		}
		int start = 0;
		int limit = pageSize;
		if (params.get("start") != null && params.get("start").length > 0) {
			start = Integer.valueOf((params.get("start"))[0]);
		}
		if (params.get("limit") != null && params.get("limit").length > 0) {
			limit = Integer.valueOf((params.get("limit"))[0]);
		}
		if (params.get("sort") != null && params.get("sort").length > 0) {
			condition.setSortBy((params.get("sort"))[0]);
		}
		if (params.get("dir") != null && params.get("dir").length > 0) {
			condition.setSortType((params.get("dir"))[0]);
		}
		condition.setStartRow(start);
		condition.setMaxRow(limit);
		this.totalCount = customerInfoService.getTotalCount(condition);
		this.customerInfos = customerInfoService.getCustomerInfosByCondition(condition);
		return SUCCESS;
	}

	@Override
	public void setParameters(Map<String, String[]> params) {
		this.params = params;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
}

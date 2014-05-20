package com.elulian.CustomerSecurityManagementSystem.web.admin.userInfo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ParameterAware;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.elulian.CustomerSecurityManagementSystem.common.Config;
import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.UserExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IUserInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.impl.UserInfoService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

@Controller("userInfoAction") @Scope("prototype")
public class UserInfoAction extends ActionSupport implements ParameterAware {


	private static Logger logger = Logger.getLogger(UserInfoAction.class);
	
	@Autowired
	private IUserInfoService userInfoService;
	private List<UserInfo> userInfos;
	private UserInfo userInfo;
	private Integer id;
	private Condition condition;
	private Map<String, String[]> params;
	
	private boolean hasNext;

	private boolean hasPrevious;
	
	private int currentPage;

	private long totalPage;
	
	private int pageSize;

	// use to check whether current action is save/remove
	private boolean flag = false;

	public UserInfoAction() {
		/*if (userInfoService == null)
			userInfoService = new UserInfoService();*/
		// For exactly dynamic reading, should getPagesize every time, no keep in class member
		// But consider the effectivity, it is not necessary
		pageSize = getPageSizeFromConfigFile();
		// currentPage should be 1 when the object is created.
		currentPage = 1;
		// not necessary, default value.
		hasNext = false;
		hasPrevious = false;
		
	}

	public List<UserInfo> getUserInfos() {
		return userInfos;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Condition getCondition() {
		return condition;
	}

	
	 public void setCondition(Condition condition) { this.condition =
	 condition; }
	

	public String list() {
		if(null == condition){
			// access without condition
			logger.debug("First time to access userInfoAction, create a condition object");
			condition = new Condition();
		}else{
			if(condition.getRealname() != null && condition.getRealname().length() ==0)
				 condition.setRealname(null);
			if(condition.getUsername() != null && condition.getUsername().length() ==0)
				 condition.setUsername(null);
			if(condition.getSearchBranch() != null && condition.getSearchBranch().length() ==0)
				 condition.setSearchBranch(null);
		}
		
		long total = userInfoService.getTotalCount(condition);
		totalPage = (total % pageSize == 0) ? (total / pageSize) : (total
				/ pageSize + 1);
		
		if (!flag && params.get("pageNum") != null) {
			int pageNum = Integer.valueOf((params.get("pageNum"))[0]);
			logger.debug(pageNum);
			logger.debug("********************");
			if (pageNum >= 1 && pageNum <= totalPage) {
				currentPage = pageNum;
			}
		} else if (flag) {
			currentPage = 1;
		}
		
		
		condition.setStartRow((currentPage - 1) * pageSize);
		condition.setMaxRow(pageSize);
		// this.totalPage =
		// (int)Math.ceil((double)service.getTotalCount(condition) / pageSize);
		
		
		hasNext = (currentPage < totalPage) ? true : false;
		hasPrevious = (currentPage <= 1) ? false : true;

		this.userInfos = userInfoService.getUserInfosByCondition(condition);
		flag = false;
		return Action.SUCCESS;
	}

	/*
	 * public String execute() { return Action.SUCCESS; }
	 */

	public String save() {
		// give a default password if create userInfo and jump to the first page
		if (userInfo.getId() == null) {
			userInfo.setPassword("user123456");
			// currentPage = 1;
			flag = true;
		}
		logger.debug(logger.getName() + userInfo.getUsername());
		try {
			this.userInfoService.save(userInfo);
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataAccessApiUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.userInfo = new UserInfo();
		return list();
	}

	public String remove() {
		// currentPage = 1;
		flag = true;
		userInfoService.remove(id);
		//condition.setStartRow(0);
		return list();
	}

	public void prepare() throws Exception {
		if (id != null)
			userInfo = userInfoService.findById(id);
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPageSizeFromConfigFile(){
		return Config.getInstance().getPageSizeByKey("pageSize");
	}

	@Override
	public void setParameters(Map<String, String[]> params) {
		this.params = params;
	}
}

package com.elulian.CustomerSecurityManagementSystem.vo;

public class Condition {
	
	//Common Search condition
	private String searchBranch;
	private int startRow;
	private int maxRow;

	// Sort by which attribute
	private String sortBy;
	// DESC or ASC 
	private String sortType;
	
	@Deprecated
	private String branch;	
	
	//CustomerInfo Search condition
	private Integer riskMinValue;
	private Integer riskMaxValue;
	private String customerId;

	// UserInfo Search condition
	private String username;
	private String realname;
	
	public Condition( ) {
		
	}
	
	public Condition(String searchBranch, int startRow, int maxRow,
			String sortBy, String branch, Integer riskMinValue,
			Integer riskMaxValue, String customerId, String username,
			String realname, String sortType) {
		super();
		this.searchBranch = searchBranch;
		this.startRow = startRow;
		this.maxRow = maxRow;
		this.sortBy = sortBy;
		this.branch = branch;
		this.riskMinValue = riskMinValue;
		this.riskMaxValue = riskMaxValue;
		this.customerId = customerId;
		this.username = username;
		this.realname = realname;
		this.sortType = sortType;
	}

	public String getRealname() {
		return realname;
	}


	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSearchBranch() {
		return searchBranch;
	}
	public void setSearchBranch(String searchBranch) {
		this.searchBranch = searchBranch;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getRiskMinValue() {
		return riskMinValue;
	}
	public void setRiskMinValue(Integer riskMinValue2) {
		this.riskMinValue = riskMinValue2;
	}
	public Integer getRiskMaxValue() {
		return riskMaxValue;
	}
	public void setRiskMaxValue(Integer riskMaxValue) {
		this.riskMaxValue = riskMaxValue;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}

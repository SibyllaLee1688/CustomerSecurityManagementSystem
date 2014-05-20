package com.elulian.CustomerSecurityManagementSystem.service;

import java.util.List;

public class RuleData {

	// The unique id for the RuledData
	private Integer id;
	// The customerInfo attribute to check
	private String attribute;
	// The attribute type, e.g. java.util.Date
	private String type;
	// The check method, e.g. =, !=, >, <
	private String condition;
	// Condition params list
	private List<String> params;
	// Risk value for this rule
	private Integer value;
	// A short description for this rule
	private String description;

	public RuleData() {
		super();
	}

	public RuleData(Integer id, String attribute, String type,
			String condition, List<String> params, Integer value,
			String description) {
		super();
		this.id = id;
		this.attribute = attribute;
		this.type = type;
		this.condition = condition;
		this.params = params;
		this.value = value;
		this.description = description;
	}

	public RuleData(String attribute, String type, String condition,
			List<String> params, Integer value, String description) {
		super();
		this.attribute = attribute;
		this.type = type;
		this.condition = condition;
		this.params = params;
		this.value = value;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Creates a copy of this object. This implementation will create a deep
	 * clone, i.e. the List params that stores the params is cloned, too. So changes
	 * performed at the copy won't affect the original and vice versa.
	 * 
	 * @return the copy
	 * @deprecated not implement yet
	 */
	@Deprecated
	public Object clone() {
		try {
			RuleData copy = (RuleData) super.clone();
//			CollectionUtils.
			// copy.params = params..clone();
			return copy;
		} catch (CloneNotSupportedException cex) {
			// should not happen
			throw new RuntimeException(cex);
		}
	}

}

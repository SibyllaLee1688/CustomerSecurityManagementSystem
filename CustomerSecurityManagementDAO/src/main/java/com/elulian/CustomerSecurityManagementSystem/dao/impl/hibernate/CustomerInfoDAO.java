/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.hibernate;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.ICustomerInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

/**
 * @author elulian
 *
 */
@Repository("customerInfoDAO")
public class CustomerInfoDAO extends BaseDAO<CustomerInfo, Integer> implements
		ICustomerInfoDAO {

	private static Logger logger = LoggerFactory.getLogger(CustomerInfoDAO.class);
	
	@Override
	public long getTotalCount(Condition condition) {
		return super.getTotalCount(convertConditionToHql(condition, true), "customerInfo");
	}

	@Override
	public List<CustomerInfo> getCustomerInfoByCondition(Condition condition) {
	
		// System.out.println("AssignmentDAO----> " + hql);
		return super.getOnePage(convertConditionToHql(condition, false), condition.getStartRow(),
				condition.getMaxRow());
	}
	
	private String convertConditionToHql(Condition condition, boolean isCount){
		boolean flag = false;
		StringBuffer hql = new StringBuffer(
				"select customerInfo from CustomerInfo customerInfo where ");
		if (condition.getSearchBranch() != null && condition.getSearchBranch().length() != 0 && (!condition.getSearchBranch().equalsIgnoreCase("ALL"))) {
			flag = true;
			hql.append("customerInfo.branch like '%" + condition.getSearchBranch()
					+ "%'");
//			hql.append("customerInfo.branch like: '%" + condition.getSearchBranch()	+ "%' ");
		}
		if (condition.getCustomerId() != null && condition.getCustomerId().length() != 0 && condition.getCustomerId().length() != 0) {
			if(flag)
				hql.append(" and ");
			flag = true;
			hql.append("customerInfo.customerId = '" + condition.getCustomerId()
					+ "' ");
		}
		if (condition.getRiskMinValue() != null && condition.getRiskMinValue() !=  0) {
			if(flag)
				hql.append(" and ");
			flag = true;
			hql.append("customerInfo.riskValue >= " + condition.getRiskMinValue());
		}
		if (condition.getRiskMaxValue() != null && condition.getRiskMaxValue() != 0) {
			if(flag)
				hql.append(" and ");
			flag = true;
			hql.append("customerInfo.riskValue <= " + condition.getRiskMaxValue());
		}
		if(!isCount){
			String sortBy = condition.getSortBy();
			String sortType = condition.getSortType();
			// Check the sortBy string is one of CustomerInfo's attribute, actually i think it should be checked in business layer
			if(!isValidateAttribute(sortBy)){
				logger.debug("Unknow CustomerInfo attribute to sort: " + sortBy + ", set to default: riskValue");
				sortBy = "riskValue";
			}
			if(!isValidateSortType(sortType)){
				logger.debug("Unknow Sort type: " + sortType + ", set to default: desc");
				sortType = "desc";
			}
			if (!flag) {
				hql = new StringBuffer(
						" select customerInfo from CustomerInfo customerInfo order by customerInfo." + sortBy + " " + sortType);
			} else {
				hql.append(" order by customerInfo." + sortBy + " " + sortType);
			}
		} else {
			if (!flag) {
				hql = new StringBuffer(
						" select customerInfo from CustomerInfo customerInfo");
			}
		}
		
		logger.debug(hql.toString());
		return hql.toString();
	}

	private boolean isValidateAttribute(String sortBy) {
		if(sortBy == null || sortBy.length() == 0) 
			return false;
		for(Field field : CustomerInfo.class.getDeclaredFields()){
			if(field.getName().equals(sortBy))
				return true;
		}
		return false;
	}

	private boolean isValidateSortType(String sortType) {
		if(sortType == null || (!sortType.equalsIgnoreCase("asc") && !sortType.equalsIgnoreCase("desc"))) 
			return false;
		return true;
	}

}

/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IUserInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

/**
 * @author elulian
 *
 */
@Repository("userInfoDAO")
public class UserInfoDAO extends BaseDAO<UserInfo, Integer> implements
IUserInfoDAO {

private static final Logger logger = LoggerFactory.getLogger(UserInfoDAO.class);
	
	@Override
	public UserInfo getUserByName(String name) {
		Query q = getSession().createQuery("select t from UserInfo t where t.username = '"
				+ name + "'");
		
		@SuppressWarnings("unchecked")
		List<UserInfo> list = q.list();
		if(list.isEmpty())
			return null;
		return list.get(0);
	}

	@Override
	public long getTotalCount(Condition condition) {
		StringBuffer hql = new StringBuffer(
				"select userInfo from UserInfo userInfo where ");
		boolean flag = false;
		if (condition.getSearchBranch() != null
				&& condition.getSearchBranch().length() != 0
				&& (!condition.getSearchBranch().equalsIgnoreCase("ALL"))) {
			flag = true;
			hql.append("userInfo.branch like '%" + condition.getSearchBranch()
					+ "%' ");
		}
		if (condition.getUsername() != null
				&& condition.getUsername().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.username = '" + condition.getUsername() + "' ");
		}
		if (null != condition.getRealname()
				&& 0 != condition.getRealname().length()) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.realname = '" + condition.getRealname() + "' ");
		}
		if (!flag) {
			hql = new StringBuffer(" select userInfo from UserInfo userInfo ");
		}
		return super.getTotalCount(hql.toString(), "userInfo");
	}

	/**
	 * @param condition should not be null
	 * @return Result list (UserInfo), list is empty if no result found
	 */
	@Override
	public List<UserInfo> getUserInfoByCondition(Condition condition) {
		boolean flag = false;
		StringBuffer hql = new StringBuffer(
				"select userInfo from UserInfo userInfo where ");
		if (condition.getSearchBranch() != null
				&& condition.getSearchBranch().length() != 0
				&& (!condition.getSearchBranch().equalsIgnoreCase("ALL"))) {
			flag = true;
			hql.append("userInfo.branch like '%" + condition.getSearchBranch()
					+ "%' ");
		}
		if (condition.getUsername() != null
				&& condition.getUsername().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.username = '" + condition.getUsername() + "' ");
		}
		if (condition.getRealname() != null
				&& condition.getRealname().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.realname = '" + condition.getRealname() + "' ");
		}
		if (!flag) {
			hql = new StringBuffer(
					" select userInfo from UserInfo userInfo order by userInfo.branch desc");
		} else {
			hql.append(" order by userInfo.branch desc");
		}
		logger.debug("UserInfoDAO user condition to hql----> " + hql);
		return super.getOnePage(hql.toString(), condition.getStartRow(),
				condition.getMaxRow());
	}

}

/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IRoleDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;

/**
 * @author elulian
 *
 */
@Repository("roleDAO")
public class RoleDAO extends BaseDAO<Role, Integer> implements
		IRoleDAO {

	/* (non-Javadoc)
	 * @see com.elulian.CustomerSecurityManagementSystem.dao.IRoleDAO#getRoleByName(java.lang.String)
	 */
	@Override
	public Role getRoleByName(String name) {
		return (Role) getSession().getNamedQuery("findRoleByName").uniqueResult();
	}
}

/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao;

import com.elulian.CustomerSecurityManagementSystem.vo.Role;

/**
 * @author cloudlu
 *
 */
public interface IRoleDAO extends IBaseDAO<Role, Integer> {

	
	public Role getRoleByName(String name);
}

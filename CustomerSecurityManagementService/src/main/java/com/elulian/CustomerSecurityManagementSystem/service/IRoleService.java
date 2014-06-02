/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service;

import com.elulian.CustomerSecurityManagementSystem.vo.Role;

/**
 * @author cloud lu
 *
 */
public interface IRoleService extends IBaseService<Role, Integer> {
	
	public Role getRoleByName(String name);
}

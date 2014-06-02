/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elulian.CustomerSecurityManagementSystem.dao.IRoleDAO;
import com.elulian.CustomerSecurityManagementSystem.service.IRoleService;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;

/**
 * @author cloud lu
 *
 */
@Service("roleService")
public class RoleService extends BaseService<Role, Integer> implements IRoleService {

	
	private IRoleDAO roleDAO;

    @Autowired
    public void setRoleDAO(IRoleDAO roleDAO) {
        this.dao = roleDAO;
        this.roleDAO = roleDAO;
    }
	
	/* (non-Javadoc)
	 * @see com.elulian.CustomerSecurityManagementSystem.service.IRoleService#getRoleByName(java.lang.String)
	 */
	@Override
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}

}

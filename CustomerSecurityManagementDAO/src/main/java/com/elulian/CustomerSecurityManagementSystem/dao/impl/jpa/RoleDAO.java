/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.jpa;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IRoleDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;

/**
 * @author cloud lu
 *
 */
@Repository("roleDAO")
public class RoleDAO extends BaseDAO<Role, Integer> implements IRoleDAO {

	@Override
	public Role getRoleByName(String name) {
		return (Role) entityManager.createNamedQuery("findRoleByName").getResultList().get(0);
	}

/*	@Override
	public void remove(Role object){
		
	}


	@Override
    public void remove(Integer id){
		
	}*/
}

/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

/**
 * @author cloud lu
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:**/applicationContext-resources.xml",
                "classpath:**/security.xml",
                "classpath:**/applicationContext-dao.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class RoleDAOTest {

	private static Logger logger = LoggerFactory.getLogger(RoleDAOTest.class);
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private IUserInfoDAO userInfoDAO;

	@Test
	public void add(){
		logger.info("---------------------add-------------");
		Role role = new Role();
		role.setName("add");
		assertNull(role.getId());
		role = roleDAO.save(role);
		assertNotNull(role.getId());
	}
	
	//@Test (expected = org.apache.openjpa.persistence.InvalidStateException.class)
	//@Test (expected = javax.persistence.PersistenceException.class) 
	//@Test (expected = org.springframework.dao.InvalidDataAccessApiUsageException.class)
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void addNullName(){
		logger.info("---------------------addNullName-------------");
		roleDAO.save(new Role());
		roleDAO.findAll();
	}
	
	//@Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
	//@Test (expected = javax.persistence.PersistenceException.class) //org.hibernate.exception.ConstraintViolationException
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void addDuplicatedName(){
		logger.info("---------------------addDuplicatedName-------------");
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		roleDAO.save(role);
		roleDAO.findAll();
	}
	
	@Test
	public void searchById(){
		logger.info("---------------------searchById-------------");
		Role role = roleDAO.findById(1);
		assertNotNull(role.getId());
		assertEquals("ROLE_ADMIN", role.getName());
	}

	@Test
	public void getRoleUsers(){
		logger.info("---------------------getRoleUsers-------------");
		Role role = roleDAO.findById(1);
		assertNotNull(role.getUsers());
		assertTrue(0 != role.getUsers().size());
	}
	
	@Test
	public void searchAll(){
		logger.info("---------------------searchAll-------------");
		assertEquals(3, roleDAO.findAll().size());
	}

	@Test
	public void edit(){
		logger.info("---------------------edit-------------");
		Role role = roleDAO.findById(2);
		assertEquals("ROLE_USER", role.getName());
		assertEquals("Default role for registered Users", role.getDescription());
		role.setName("edit");
		role.setDescription("edit");
		roleDAO.save(role);
		role = roleDAO.findById(2);
		assertEquals("edit", role.getName());
		assertEquals("edit", role.getDescription());
	}
	
	@Test
	public void removeNoUserRole(){
		logger.info("---------------------removeNoUserRole-------------");
		Role role = roleDAO.findById(3);
		assertEquals("ROLE_ANONYMOUS", role.getName());
		roleDAO.remove(role);
		assertNull(roleDAO.findById(3));
	}
	
	@Test
	public void removeUserExistsRole(){
		logger.info("---------------------removeUserExistsRole-------------");
		/* for openjpa, relationship in user_role is automatic removed
		 * but for hibernate jpa, the remove does not do anything and it requires to remove relationship first*/
		UserInfo userInfo = userInfoDAO.findById(1);
		Role role = roleDAO.findById(1);
		assertEquals("ROLE_ADMIN", role.getName());
		assertEquals(role.getName(), ((Role)userInfo.getRoles().toArray()[0]).getName());
		roleDAO.remove(role);
		assertNull(roleDAO.findById(1));
		/* no reference/CascadeType from role to user, carefully for the remove action */
		assertEquals(role.getName(), ((Role)userInfo.getRoles().toArray()[0]).getName());
		 
		assertEquals(1, userInfoDAO.findById(3).getRoles().size());
		
		assertEquals(1, userInfoDAO.findById(1).getRoles().size());
	}
}

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

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

/**
 * Customer DAO CRUD and validation tests without transaction
 * 
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
public class CustomerInfoDAOTest {

	private static Logger logger = LoggerFactory.getLogger(CustomerInfoDAOTest.class);
	
	@Autowired
	private ICustomerInfoDAO customerInfoDAO;

	@Test
	public void add(){
		logger.info("---------------------add-------------");
		CustomerInfo info = new CustomerInfo();
		info.setCustomerName("customerName");
		info.setCustomerId("customerId");
		info = customerInfoDAO.save(info);
		assertNotNull(info.getId());
	}
	
	//@Test (expected = org.apache.openjpa.persistence.InvalidStateException.class)
	//@Test (expected = javax.persistence.PersistenceException.class) 
	//@Test (expected = org.springframework.dao.InvalidDataAccessApiUsageException.class)
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void addNullName(){
		logger.info("---------------------addNullName-------------");
		customerInfoDAO.save(new CustomerInfo());
		customerInfoDAO.findAll();
	}
	
	//@Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
	//@Test (expected = javax.persistence.PersistenceException.class) //org.hibernate.exception.ConstraintViolationException
	@Test (expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void addDuplicatedCustomerId(){
		logger.info("---------------------addDuplicatedCustomerId-------------");
		CustomerInfo info = new CustomerInfo();
		info.setCustomerId("1");
		customerInfoDAO.save(info);
		customerInfoDAO.findAll();
	}
	
	@Test
	public void searchById(){
		logger.info("---------------------searchById-------------");
		CustomerInfo info = customerInfoDAO.findById(1);
		assertNotNull(info.getId());
		assertEquals("1", info.getCustomerId());
	}
}

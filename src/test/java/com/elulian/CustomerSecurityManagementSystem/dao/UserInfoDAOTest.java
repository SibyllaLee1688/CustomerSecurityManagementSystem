package com.elulian.CustomerSecurityManagementSystem.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.elulian.CustomerSecurityManagementSystem.BaseTest;
import com.elulian.CustomerSecurityManagementSystem.dao.DAOFactory;
import com.elulian.CustomerSecurityManagementSystem.dao.IUserInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;  
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;  
  
import org.junit.Test;  
import org.junit.runner.RunWith;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.transaction.TransactionConfiguration;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {//"classpath:applicationContext-resources.xml",
             //   "classpath:applicationContext-dao.xml",
                //"classpath:applicationContext-service.xml",
                "classpath:**/security.xml",
                "classpath:**/applicationContext*.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class UserInfoDAOTest extends BaseTest{

	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUserInfoDAO userInfoDAO;
	
	@Autowired(required = false)
	private SaltSource saltSource;

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private Condition con;
	private UserInfo user; 
	// private UserInfo user2; 
//	 private UserInfo user3; 
	 
	 
	private final String specialUsername = "Admin";
	private final String specialRealname = "elulian";

	private String normalUsername = "userdao";

	private String normalRealname = "userdao";

	
	@Before
	public void setUp() {
		
		/*UserInfo specialUserInfo = userInfoDAO
				.getUserByName(specialUsername);

		if (null == specialUserInfo) {

			UserInfo userInfo = new UserInfo();
			Calendar cl = Calendar.getInstance();
			Date registerTime = cl.getTime();
			cl.add(Calendar.YEAR, 60);
			Date expiredTime = cl.getTime();

			buildUser(userInfo, specialUsername, specialRealname, registerTime,
					expiredTime, "cloud.lu@ericsson.com","ALL");
			
			userInfoDAO.save(userInfo);
		}
		
		UserInfo normalUserInfo = userInfoDAO
				.getUserByName(normalUsername);

		if (null == normalUserInfo) {

			UserInfo userInfo = new UserInfo();
			Calendar cl = Calendar.getInstance();
			Date registerTime = cl.getTime();
			cl.add(Calendar.YEAR, 60);
			Date expiredTime = cl.getTime();

			buildUser(userInfo, normalUsername, normalRealname, registerTime,
					expiredTime, "userdao@ericsson.com","北京");
			
			userInfoDAO.save(userInfo);
		}*/

	}
	 
	 private void buildUser(UserInfo userInfo, String username, String realname, Date registerTime, Date expiredTime, String email, String branch){
		 userInfo.setUsername(username);
    	 userInfo.setRealname(realname);
    	 userInfo.setBranch(branch);
    	 userInfo.setPassword("password");
    	 userInfo.setAccountExpired(false);
    	 userInfo.setAccountLocked(false);
    	 userInfo.setCredentialsExpired(false);
    	 userInfo.setEnabled(true);
    	 userInfo.setEmail(email);
    	 userInfo.setExpiredTime(expiredTime);
    	 userInfo.setPasswordHint("passwordHint");
    	 userInfo.setPhoneNumber("75728");
    	 userInfo.setRegisterTime(registerTime);
    	 if (saltSource == null) {
             userInfo.setPassword(passwordEncoder.encodePassword(userInfo.getPassword(), null));
         } else {
             userInfo.setPassword(passwordEncoder.encodePassword(userInfo.getPassword(),
                     saltSource.getSalt(userInfo)));
         }
    	 
    	 
//    	 userInfo.setRoles(roles);
	 }
	 
	 /* test save */
	 /**
	  * test add a new user into database
	  */
     @Test
     public void testDAOAddUser(){
    	 String username = "AdminUserDAO";
    	 String userId = "cloudluDAO";
    	 UserInfo userInfo = new UserInfo();
    	 Calendar cl = Calendar.getInstance();
    	 Date registerTime = cl.getTime();
    	 cl.add(Calendar.MONTH, 6);
    	 Date expiredTime = cl.getTime(); 
    	 buildUser(userInfo, username, userId, registerTime, expiredTime, (username + "@gmail.com"),"ALL");
    	 assertNull(userInfo.getId());
    	 assertEquals("ROLE_ADMIN", ((Role)(userInfo.getRoles().toArray()[0])).getName());
    	 logger.info("add a new user to database--------------");
    	 user = userInfoDAO.save(userInfo);
    	 assertNotNull(user.getId());
    	 assertEquals(registerTime, user.getRegisterTime());
    	 assertEquals(expiredTime, user.getExpiredTime());
    	 
     }
     
     /**
      * test add a exists username user to database
      */
    /* @Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     public void testDAOAddExistsUsernameUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, username, userId, registerTime, expiredTime, "testemail");
    	 assertNull(user.getId());
    	 logger.info("test add the same username user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  
    	 userInfoDAO.findAll();
     }	 */
    
     /**
      * test add a exists email user to database
      */
     /*@Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     public void testDAOAddExistsEmailUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, username, userId, registerTime, expiredTime, "email");
    	 user.setUsername("emailConflict");
    	 user.setEmail(username + "@gmail.com");
    	 assertNull(user.getId());
    	 logger.info("test add the same email user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  
    	 userInfoDAO.findAll();
     }*/
     
     /**
      * test add a miss data user into database
      */
     @Test(expected = org.apache.openjpa.persistence.InvalidStateException.class)
     public void testDAOAddMissingInfoUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, "username", "realname", registerTime, expiredTime, "email","ALL");
    	 user.setPhoneNumber(null);
    	 logger.info("test add miss data user to database-------------");
    	 userInfoDAO.save(user);
    	 /* due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  */
    	 userInfoDAO.findAll();
     }
     
	 /**
	  * Test case should be modified if admin is not username in database
	  * or "" is username in database
	  */
     @Test
     public void testDAOGetUserByName(){    					 
    	 // Test get all userInfos with username: admin
    	 logger.info("test search user by name: " + specialUsername + "-------------");
    	 UserInfo userInfo = userInfoDAO.getUserByName(specialUsername);
    	 assertNotNull(userInfo);
    	 assertEquals(specialUsername, userInfo.getUsername());
    	 
    	 // Test get all userInfos with usrname not exist, like: ""
    	 logger.info("test search user by no exists name, like: ''-------------");
    	 userInfo = userInfoDAO.getUserByName("");
    	 assertNull(userInfo);
    	 
    	 //Test get all userInfos with username null
    	 userInfo = userInfoDAO.getUserByName(null);
    	 logger.info("test search user by null name-------------");
    	 assertNull(userInfo);
     }
     
   
     /**
	  * Test case should be modified in below conditions:
	  * 1. Total userInfos is not 12 in database.
	  * 2. Total userInfos with branch like 1 is not 2 in database.
	  * 3. Total userInfos with username equals admin is not 1
	  * 3. Total userInfos with userId equals admin is not 1 
	 
     @Test (expected=NullPointerException.class)  public void testGetTotalCount(){
    	 con = new Condition();
    	 //Count all userInfos in database, should modify
    	 assertEquals(12, dao.getTotalCount(con));
    	 
    	 //Count all userInfos with branch equals ALL
    	 con.setSearchBranch("ALL");
    	 assertEquals(12, dao.getTotalCount(con));
    	 
    	//Count all userInfos with branch like 1
    	 con.setSearchBranch("1");
    	 assertEquals(2, dao.getTotalCount(con));
    	 
    	 //Count username equals admin userInfos
    	 con.setSearchBranch(null);
    	 con.setUsername("admin");
    	 assertEquals(1, dao.getTotalCount(con));
    	//Count userId equals userId userInfos
    	 con.setUserId("admin");
    	 con.setUsername(null);
    	 assertEquals(1, dao.getTotalCount(con));
    	 
    	 
    	 //  Count with mixed conditions:
    	 //  1. branch and userId
    	  
    	  
    	 
    	 
    	 // Test throw NullPointerException
    	 dao.getTotalCount(null);
     }

     @Test public void testGetUserInfoByCondition(){
    	 
     }
     
*/
     @Test 
     public void testDAORemoveUser(){
    	 int id = 1;   	 
    	 UserInfo userInfo = userInfoDAO.findById(id);
    	 assertNotNull(userInfo);
    	/* logger.info("test delete user by Id: " + id  + " -------------");
    	 userInfoDAO.remove(userInfo.getId());
    	 assertNull(userInfoDAO.findById(id));*/
    	 
    	 id = 55;
    	 userInfo = userInfoDAO.findById(id);
    	 assertNotNull(userInfo);
    	 logger.info("test delete user by object: " + userInfo  + " -------------");
    	 userInfoDAO.remove(userInfo);
    	 assertNull(userInfoDAO.findById(id));
    	 
     }
    
     
     @Test
     public void testDAOGetTotalUser(){
    	 
    	 long userCount = userInfoDAO.getTotalCount();
    	 
    	 assertNotNull(userCount);
    	 
    	 System.out.println("===========================" + userCount + "===========================");
     }
}

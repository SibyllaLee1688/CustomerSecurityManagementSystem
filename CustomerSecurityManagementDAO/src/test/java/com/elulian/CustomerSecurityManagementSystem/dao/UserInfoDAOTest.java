package com.elulian.CustomerSecurityManagementSystem.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(
        locations = {"classpath:**/applicationContext-resources.xml",
             //   "classpath:applicationContext-dao.xml",
                //"classpath:applicationContext-service.xml",
                "classpath:**/security.xml",
                "classpath:**/applicationContext-dao.xml"})
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true) 
public class UserInfoDAOTest {
	
	private static Logger logger = Logger.getLogger(UserInfoDAOTest.class);

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private IUserInfoDAO userInfoDAO;

	private Condition con;
	private UserInfo user; 
	 
	private final String specialUsername = "Admin";
	private final String specialRealname = "elulian";

	private final String removeUsername = "remove";
	private final String removeRealname = "remove";
	
	private final String addUsername = "add";
	private final String addRealname = "add";	
	
	private String normalUsername = "userdao";
	private String normalRealname = "userdao";

	
	/**
	 * execute before each test, for once execution, use BeforeClass static
	 */
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
					expiredTime, "luliang1984@gmail.com","ALL");
			
			logger.info(userInfo);
			
			userInfoDAO.save(userInfo);
		}

		UserInfo removeUserInfo = userInfoDAO
				.getUserByName(removeUsername);

		if (null == removeUserInfo) {

			UserInfo userInfo = new UserInfo();
			Calendar cl = Calendar.getInstance();
			Date registerTime = cl.getTime();
			cl.add(Calendar.YEAR, 60);
			Date expiredTime = cl.getTime();

			buildUser(userInfo, removeUsername, removeRealname, registerTime,
					expiredTime, "userdao@***.com","北京");
			
			logger.info(userInfo);
			
			userInfoDAO.save(userInfo);
		}*/

	}
	
	@After
	public void tearDown(){
		/*UserInfo userInfo = userInfoDAO.getUserByName(addUsername);
		assertNotNull(userInfo);
		userInfoDAO.remove(userInfo);

		UserInfo removeUserInfo = userInfoDAO.getUserByName(removeUsername);

		assertNull(removeUserInfo);
		removeUserInfo = new UserInfo();
		Calendar cl = Calendar.getInstance();
		Date registerTime = cl.getTime();
		cl.add(Calendar.YEAR, 60);
		Date expiredTime = cl.getTime();

		buildUser(userInfo, removeUsername, removeRealname, registerTime,
				expiredTime, "userdao@***.com", "北京");

		userInfoDAO.save(userInfo);*/
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
    	 userInfo.setPhoneNumber("7572875728");
    	 userInfo.setRegisterTime(registerTime);
    	 userInfo.setPassword(bcryptEncoder.encode(userInfo.getPassword()));
	 }
	 
	 /* test save */
	 /**
	  * test add a new user into database
	  */
     @Test
     public void testDAOAddUser(){
    	 String username = "addTest";
    	 String userId = "addTest";
    	 UserInfo userInfo = new UserInfo();
    	 Calendar cl = Calendar.getInstance();
    	 Date registerTime = cl.getTime();
    	 cl.add(Calendar.MONTH, 6);
    	 Date expiredTime = cl.getTime(); 
    	 buildUser(userInfo, username, userId, registerTime, expiredTime, (username + "@gmail.com"),"ALL");
    	 assertNull(userInfo.getId());
    	 assertEquals("ROLE_ADMIN", ((Role)(userInfo.getRoles().toArray()[0])).getName());
    	 logger.info("add a new user to database--------------");
    	 long startTime = System.currentTimeMillis();
    	 user = userInfoDAO.save(userInfo);
    	 long endTime = System.currentTimeMillis();
    	 logger.info(endTime - startTime);
    	 assertNotNull(user.getId());
    	 assertEquals(registerTime, user.getRegisterTime());
    	 assertEquals(expiredTime, user.getExpiredTime());
    	 //userInfoDAO.remove(user.getId());    	 
     }
     
     /**
      * test add a exists username user to database
     * @throws InterruptedException 
      */
     @Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     public void testDAOAddExistsUsernameUser() throws InterruptedException{
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, specialUsername, specialRealname, registerTime, expiredTime, "abdc@gmail.com", "ALL");
    	 assertNull(user.getId());
    	 logger.info("test add the same username user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  /*due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa
    	  */
    	// Thread.sleep(1000);
    	 userInfoDAO.findAll();
     }	 
    
     /**
      * test add a exists email user to database
      */
     @Test (expected = org.apache.openjpa.persistence.EntityExistsException.class)
     public void testDAOAddExistsEmailUser(){
    	 Date registerTime = null;
    	 Date expiredTime = null; 
    	 UserInfo user = new UserInfo();
    	 Calendar clr = Calendar.getInstance();
    	 registerTime = clr.getTime();
    	 clr.add(Calendar.MONTH, 6);
    	 expiredTime = clr.getTime(); 
    	 buildUser(user, "emailConflict", "emailConflict", registerTime, expiredTime, "luliang1984@gmail.com", "ALL");
    	 assertNull(user.getId());
    	 logger.info("test add the same email user to database-------------");
    	 userInfoDAO.save(user);
    	 assertNull(user.getId());
    	  /*due to defaul rollback is true, need to do some more operation to trigger actual
    	  * save operation and exception in openjpa*/
    	  
    	 userInfoDAO.findAll();
     }
     
     /**
      * test add a miss data user into database, use spring,
      */
     @Test(expected=org.apache.openjpa.persistence.InvalidStateException.class)
     //org.springframework.dao.InvalidDataAccessApiUsageException.class for commit to database
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
    	 userInfoDAO.findAll();
     }
     
	 /**
	  * Test search 
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
     public void testDAOFindUser(){
    	 int id = 1;   	 
    	 UserInfo userInfo = userInfoDAO.findById(id);
    	 assertNotNull(userInfo);
     }    
     
     @Test
     public void testDAOGetTotalUser(){
    	 
    	 long userCount = userInfoDAO.getTotalCount();
    	 
    	 assertNotNull(userCount);
    	 
    	 logger.info("===========================" + userCount + "===========================");
     }
     
     /** test remove */
     @Test
     public void testDAORemoveUser(){
    	 UserInfo userInfo = null;
    	 userInfo = userInfoDAO.getUserByName(removeUsername);
    	 int id =userInfo.getId();
    	 assertNotNull(userInfo);
    	 logger.info("test delete user by object: " + userInfo  + " -------------");
    	 userInfoDAO.remove(userInfo);
    	 assertNull(userInfoDAO.findById(id));  	 
     }
}

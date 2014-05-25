package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-resources.xml",
// "classpath:applicationContext-dao.xml",
// "classpath:applicationContext-service.xml",
	"classpath:**/security.xml",
	"classpath:**/applicationContext*.xml" })
 
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)   
@Transactional
public class UserInfoServiceTest {

	
	private static  final Logger logger = Logger.getLogger(UserInfoServiceTest.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired(required = false)
	private SaltSource slatSource;
	
	@Autowired
	private IUserInfoService userInfoService;

	private Condition con;
	private UserInfo user;

	private final String specialUsername = "Admin";
	private final String specialRealname = "elulian";

	private final String normalUsername = "add";

	private final String normalRealname = "add";
	
	private String password = "password";

	private Mockery context = null;
	
	@Before
	public void setUp() {
		
		/* need to consider security testing in service layer */
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("Admin","password"));

		context = new Mockery();
		/*UserInfo specialUserInfo = userInfoService
				.getUserInfoByName(specialUsername);

		if (null == specialUserInfo) {

			UserInfo userInfo = new UserInfo();
			Calendar cl = Calendar.getInstance();
			Date registerTime = cl.getTime();
			cl.add(Calendar.YEAR, 60);
			Date expiredTime = cl.getTime();

			buildUser(userInfo, specialUsername, specialRealname, registerTime,
					expiredTime, "cloud.lu@ericsson.com","ALL");
			
			userInfoService.save(userInfo);
		}
		
		UserInfo normalUserInfo = userInfoService
				.getUserInfoByName(normalUsername);

		if (null == normalUserInfo) {

			UserInfo userInfo = new UserInfo();
			Calendar cl = Calendar.getInstance();
			Date registerTime = cl.getTime();
			cl.add(Calendar.YEAR, 60);
			Date expiredTime = cl.getTime();

			buildUser(userInfo, normalUsername, normalRealname, registerTime,
					expiredTime, "userservice@ericsson.com", "北京");
			
			userInfoService.save(userInfo);
		}
*/
	}

	private void buildUser(UserInfo userInfo, String username, String realname,
			Date registerTime, Date expiredTime, String email, String branch) {
		userInfo.setUsername(username);
		userInfo.setRealname(realname);
		userInfo.setBranch(branch);
		userInfo.setPassword(password);
		userInfo.setAccountExpired(false);
		userInfo.setAccountLocked(false);
		userInfo.setCredentialsExpired(false);
		userInfo.setEnabled(true);
		userInfo.setEmail(email);
		userInfo.setExpiredTime(expiredTime);
		userInfo.setPasswordHint("passwordHint");
		userInfo.setPhoneNumber("75728");
		userInfo.setRegisterTime(registerTime);
		// userInfo.setRoles(roles);
	}

	@Test
	public void testAddUser() {
		String username = "temp";
		String userId = "temp";	
		final UserInfo userInfo = new UserInfo();
		Calendar cl = Calendar.getInstance();
		Date registerTime = cl.getTime();
		cl.add(Calendar.MONTH, 6);
		Date expiredTime = cl.getTime();
		buildUser(userInfo, username, userId, registerTime, expiredTime,
				(username + "@gmail.com"),"ALL");
		/*final UserInfo returnUserInfo = new UserInfo();
		buildUser(returnUserInfo, username, userId, registerTime, expiredTime,
				(username + "@gmail.com"),"ALL");
		returnUserInfo.setId(5);*/
		assertNull(userInfo.getId());
		assertEquals("ROLE_ADMIN",
				((Role) (userInfo.getRoles().toArray()[0])).getName());
		logger.info("test add user------------------------"); // set up
		/*final IUserInfoDAO userInfoDAO = context.mock(IUserInfoDAO.class);
        // expectations
		userInfoService.setUserInfoDAO(userInfoDAO);
        context.checking(new Expectations() {{
            oneOf (userInfoDAO).save(with(same(userInfo))); will(returnValue(returnUserInfo));
        }});*/

        // execute
		user = userInfoService.save(userInfo); 
		 // verify
       // context.assertIsSatisfied();
		assertNotNull(user.getId());
		assertEquals(registerTime, user.getRegisterTime());
		assertEquals(expiredTime, user.getExpiredTime());
	}
	
	@Ignore // duplicate only happens in openjpa, so remove the test in service layer
	//(expected = org.apache.openjpa.persistence.EntityExistsException.class)
	public void testAddExistsUser() {
		UserInfo user = new UserInfo();
		Calendar clr = Calendar.getInstance();
		Date registerTime = clr.getTime();
		clr.add(Calendar.MONTH, 6);
		Date expiredTime = clr.getTime();
		buildUser(user, specialUsername, specialRealname, registerTime, expiredTime, "email", "ALL");
		logger.info("test add duplicated username user------------------");
		//try {
			userInfoService.save(user);
		/*} catch (ExistsException e) {
			
			logger.info("duplicated username--------------" + username + e.getMessage());
		} catch (DataMissingException e) {
			logger.info("unexpected data missing exception*********************");
		} catch (Exception e) {
			logger.info("Exception-=============------------"  + e.getClass());
		} */
	}
/*
	@Test (expected = InvalidDataAccessApiUsageException.class)
	public void testAddMissingInfoUser() throws ExistsException, DataMissingException {
		Date registerTime = null;
		Date expiredTime = null;
		UserInfo user = new UserInfo();
		Calendar clr = Calendar.getInstance();
		registerTime = clr.getTime();
		clr.add(Calendar.MONTH, 6);
		expiredTime = clr.getTime();
		buildUser(user, username, userId, registerTime, expiredTime, "email");
		user.setPhoneNumber(null);
		logger.info("test add miss data user to database-------------");
	//	try {
			userInfoService.save(user);
		} catch (ExistsException e) {
			
			logger.info("unexpected user exists exception*********************" + e.getClass());
		} catch (DataMissingException e) {
			logger.info("miss phone number for add user------=================-------"  + e.getClass());
		}  catch (Exception e) {
			logger.info("Exception-=============------------"  + e.getClass());
		}
	}
	*//**
	 * Test case should be modified if admin is not username in database or ""
	 * is username in database
	 */
	@Test
	public void testGetUserByName() {
		// Test get all userInfos with username: admin
		UserInfo info = userInfoService
				.getUserInfoByName(specialUsername);
		assertNotNull(info);
		assertEquals(specialUsername, info.getUsername());

		// Test get all userInfos with usrname not exist, like: ""
		info = userInfoService.getUserInfoByName("");
		assertNull(info);

		// Test get all userInfos with username null
		info = userInfoService.getUserInfoByName(null);
		assertNull(info);
	}

	/**
	 * Test case should be modified in below conditions: 1. Total userInfos is
	 * not 12 in database. 2. Total userInfos with branch like 1 is not 2 in
	 * database. 3. Total userInfos with username equals admin is not 1 3. Total
	 * userInfos with userId equals admin is not 1
	 * 
	 * @Test (expected=NullPointerException.class) public void
	 *       testGetTotalCount(){ con = new Condition(); //Count all userInfos
	 *       in database, should modify assertEquals(12,
	 *       dao.getTotalCount(con));
	 * 
	 *       //Count all userInfos with branch equals ALL
	 *       con.setSearchBranch("ALL"); assertEquals(12,
	 *       dao.getTotalCount(con));
	 * 
	 *       //Count all userInfos with branch like 1 con.setSearchBranch("1");
	 *       assertEquals(2, dao.getTotalCount(con));
	 * 
	 *       //Count username equals admin userInfos con.setSearchBranch(null);
	 *       con.setUsername("admin"); assertEquals(1, dao.getTotalCount(con));
	 *       //Count userId equals userId userInfos con.setUserId("admin");
	 *       con.setUsername(null); assertEquals(1, dao.getTotalCount(con));
	 * 
	 * 
	 *       // Count with mixed conditions: // 1. branch and userId
	 * 
	 * 
	 * 
	 * 
	 *       // Test throw NullPointerException dao.getTotalCount(null); }
	 */
	@Ignore
	public void testGetUserInfoByCondition() {

	}
	
	@Test
	public void testChangePassword(){
		UserInfo userInfo = userInfoService
				.getUserInfoByName(normalUsername);
		
		 //String password = "add";
		
		logger.info(passwordEncoder.matches(password, userInfo.getPassword()));
		
		//assertEquals(passwordEncoder.encodePassword(password, slatSource), userInfo.getPassword());
		
		userInfoService.changeUserPassword(normalUsername, password, normalUsername);
		
		userInfo = userInfoService
		.getUserInfoByName(normalUsername);
		
		assertTrue(passwordEncoder.matches(normalUsername, userInfo.getPassword()));
	}

}

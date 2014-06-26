/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.exception.NotExistsException;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

/**
 * @author cloud lu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-resources.xml",
	"classpath:**/security.xml",
"classpath:**/applicationContext*.xml" })
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false) 
public class PerformanceTest {

	private static final Logger logger = LoggerFactory.getLogger(PerformanceTest.class);
	
	@Autowired
	private IThresholdService thresholdService;
	
	@Autowired
	private IUserInfoService userInfoService;
	
	private String ruleVersionString = "0.1-SNAPSHOT";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}
	
	
	/**
	 * some performance test point
	 * @throws NotExistsException 
	 */
	@org.junit.Ignore
	//@Test
	public void testThresholdExeuctionTime() throws NotExistsException{		
		logger.info("----------------begin testThresholdExeuctionTime--------------");
		logger.info("init threshold service before test");
		thresholdService.initThresholdService(IThresholdService.defaultGroupId, IThresholdService.defaultArtifactId, ruleVersionString);
		monitorSetThreshold(10);
		monitorSetThreshold(100);
		monitorSetThreshold(1000);
		monitorSetThreshold(10000);
		monitorSetThreshold(100000);
		logger.info("----------------finish testThresholdExeuctionTime--------------");
	}


	/**
	 * @param recordsNumber
	 */
	private void monitorSetThreshold(long recordsNumber) {
		long start;
		long used1;
		long used2;
		start = System.currentTimeMillis();
		for(int i = 0; i < recordsNumber ; i++){
			CustomerInfo info = new CustomerInfo();
			info.setRiskType("");
			info.setRiskValue(0);
			thresholdService.setMatchThresholdsInfo(info);
		}
		used1 = System.currentTimeMillis() - start;
		logger.info("System time used by native java code : " + used1 + " with records: " + recordsNumber);
		
		start = System.currentTimeMillis();
		for(int i = 0; i < recordsNumber; i++){
			CustomerInfo info = new CustomerInfo();
			info.setRiskType("");
			info.setRiskValue(0);
			thresholdService.setCustomerThresholdsInfo(info);
		}
		used2 = System.currentTimeMillis() - start;
		logger.info("System time used by rule: " + used2 + " with records: " + recordsNumber);
		PerfInterceptor.printMethodStats();
		PerfInterceptor.clearMethodStats();
	}
	
	private void buildUser(UserInfo userInfo, String username, String realname,
			Date registerTime, Date expiredTime, String email, String branch) {
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
		// userInfo.setRoles(roles);
	}

	//@org.junit.Ignore
	@Test
	public void testUserCRUTime() {
		
		logger.info("-----------begin testUserCRUTime------------------------"); 
		monitorCDUUser(1000, 10);	
		logger.info("-----------finish testUserCRUTime------------------------");
	}


	/**
	 * @param recordsNumber
	 */
	private void monitorCDUUser(final int threads, final int records) {
		
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();    
	     ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 150, 1, TimeUnit.DAYS, queue, new RejectedExecutionHandler(){

			@Override
			public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
				logger.error(arg0.toString());
			}
	    	 
	     });
		
	     List<Future<HashMap<String, Long>>> list = new LinkedList<Future<HashMap<String, Long>>>(); 
	     
		for(int i=1; i<=threads; i++){
			list.add(executor.submit(new Callable<HashMap<String, Long>>(){
						@Override
						public HashMap<String, Long> call() throws Exception {
							SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("Admin","password"));
							HashMap<String, Long> list = new HashMap<String, Long>(records);
										
							Calendar cl = Calendar.getInstance();
							Date registerTime = cl.getTime();
							cl.add(Calendar.MONTH, 6);
							Date expiredTime = cl.getTime();
							for (int i = 0; i < records; i++){
								String username = "temp" + System.nanoTime() + Thread.currentThread().getName() + i;
								String userId = username;	
								UserInfo userInfo = new UserInfo();
								buildUser(userInfo, username, userId, registerTime, expiredTime,
										(username + "@gmail.com"),"ALL");
								long start = System.currentTimeMillis();
								userInfoService.save(userInfo);
								long end = System.currentTimeMillis();
								list.put("add", end - start);
								start = System.currentTimeMillis();
								userInfo = userInfoService.getUserInfoByName("admin");
								end = System.currentTimeMillis();
								list.put("serach", end - start);
								userInfo.setRealname("realname");
								start = System.currentTimeMillis();
								userInfoService.save(userInfo);
								end = System.currentTimeMillis();
								list.put("update", end - start);
								userInfo = userInfoService.getUserInfoByName(username);
								start = System.currentTimeMillis();
								userInfoService.remove(userInfo);
								end = System.currentTimeMillis();
								list.put("remove", end - start);
							}
							return list;
						} 
					})
			);
			
			if(i % 10 == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		for(Future<HashMap<String, Long>> future : list){
				try {
					System.out.println(future.get().toString());
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				} catch (ExecutionException e) {
					logger.error(e.getMessage(), e);
				}
		}
		executor.shutdown();
		PerfInterceptor.printMethodStats();
		PerfInterceptor.clearMethodStats();
	}
	
	@org.junit.Ignore//@Test
	public void testSearch(){
		fail();
	}
	
	@org.junit.Ignore//@Test
	public void testUpdate(){
		fail();
	}
	
	@org.junit.Ignore//@Test
	public void testDelete(){
		fail();
	}
	
	@org.junit.Ignore//@Test
	public void testMix(){
		fail();
	}
	
	@Test
	public void test(){
		
	}
}

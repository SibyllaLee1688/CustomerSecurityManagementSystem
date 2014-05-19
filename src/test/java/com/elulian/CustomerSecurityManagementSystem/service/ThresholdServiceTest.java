/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

/**
 * @author elulian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-resources.xml",
// "classpath:applicationContext-dao.xml",
// "classpath:applicationContext-service.xml",
	"classpath:**/security.xml",
"classpath:**/applicationContext*.xml" })
public class ThresholdServiceTest {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IThresholdService thresholdService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logger.info("init threshold service before test");
		thresholdService.initThresholdService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#refreshThresholdService()}.
	 */
	@Test
	public void testRefreshThresholdService() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfo() {
		CustomerInfo info = new CustomerInfo();
		thresholdService.setCustomerThresholdsInfo(info);
		logger.info(info.getRiskValue() + "======testSetCustomerThresholdsInfo========" + info.getRiskType());
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#editThresholdRulesFile()}.
	 */
	@Test
	public void testEditThresholdRulesFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#validateThresholdRulesFile()}.
	 */
	@Test
	public void testValidateThresholdRulesFile() {
		fail("Not yet implemented");
	}

}

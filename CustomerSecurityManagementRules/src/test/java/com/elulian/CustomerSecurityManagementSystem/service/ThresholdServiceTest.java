/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

/**
 * @author elulian
 *
 */
public class ThresholdServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ThresholdServiceTest.class);
	
	private static ThresholdServiceRule thresholdService = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		logger.info("init threshold service before test");
		thresholdService = new ThresholdServiceRule();
		thresholdService.initThresholdService();
   //	System.out.println("Default Charset=    ****我是*********" + Charset.defaultCharset());
   // 	System.out.println("Default Charset in Use=" + getDefaultCharSet());
    	
	}
/*	private static String getDefaultCharSet() {
    	OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
    	String enc = writer.getEncoding();
    	return enc;
    }
*/
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		thresholdService = null;
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForAllRules() {
		logger.info("======testSetCustomerThresholdsInfoForAllRulesMatched========");
		CustomerInfo info = new CustomerInfo();
		/* Integer can not be auto converted to int in drool for null object */
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(280 == info.getRiskValue());
		StringBuffer sb = new StringBuffer("Invalid professionCode value: ");
		sb.append(info.getProfessionCode()).append(" Invalid Certification End date value: ")
			.append(info.getCertificateEndDate()).append(" Customer Name is null")
			.append(" Invalid Certification ID: ").append(info.getCertificateId())
			.append(" 境外标志: ").append(info.getForeignFlag());
		assertEquals(sb.toString(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("老师");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setCustomerName("customerName");
		info.setForeignFlag(false);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
	}
	
	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForProcessionCode() {
		logger.info("======testSetCustomerThresholdsInfoForProcessionCode========");
		CustomerInfo info = new CustomerInfo();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setCustomerName("customerName");
		info.setCertificateId("123456789012345678");
		info.setForeignFlag(false);
		/* Integer can not be auto converted to int in drool for null object */
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value: " + info.getProfessionCode(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("老师");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("其他");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value: " + info.getProfessionCode(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("未知");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value: " + info.getProfessionCode(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("其它");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value: " + info.getProfessionCode(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("无业");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value: " + info.getProfessionCode(), info.getRiskType());	
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value:", info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setProfessionCode("    ");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("Invalid professionCode value:", info.getRiskType());
	}

	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForCertificateEndDateCode() {
		logger.info("======testSetCustomerThresholdsInfoForCertificateEndDateCode========");
		CustomerInfo info = new CustomerInfo();
		info.setProfessionCode("老师");
		info.setCustomerName("customerName");
		info.setCertificateId("123456789012345678");
		info.setForeignFlag(false);
		
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Invalid Certification End date value: " + info.getCertificateEndDate(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		cal.set(2009, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Invalid Certification End date value: " + info.getCertificateEndDate(), info.getRiskType());
	}
	
	
	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForCustomerName() {
		logger.info("======testSetCustomerThresholdsInfoForCustomerName========");
		CustomerInfo info = new CustomerInfo();
		info.setProfessionCode("老师");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setCertificateId("123456789012345678");
		info.setForeignFlag(false);
		
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Customer Name is null", info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setCustomerName("    ");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Customer Name is null", info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setCustomerName("customerName");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
	}
	
	
	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForCertificateId() {
		logger.info("======testSetCustomerThresholdsInfoForCertificateId========");
		CustomerInfo info = new CustomerInfo();
		info.setProfessionCode("老师");
		info.setCustomerName("customerName");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setForeignFlag(false);
		
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Invalid Certification ID: " + info.getCertificateId(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setCertificateId("   ");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(80 == info.getRiskValue());
		assertEquals("Invalid Certification ID:", info.getRiskType());
		
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setCertificateId("123456789012345678");
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
	}
	
	/**
	 * Test method for {@link com.elulian.CustomerSecurityManagementSystem.service.impl.ThresholdService#setCustomerThresholdsInfo(com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo)}.
	 */
	@Test
	public void testSetCustomerThresholdsInfoForForeignFlag() {
		logger.info("======testSetCustomerThresholdsInfoForForeignFlag========");
		CustomerInfo info = new CustomerInfo();
		info.setProfessionCode("老师");
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR)+1, Calendar.DECEMBER, 12);
		info.setCertificateEndDate(cal.getTime());
		info.setCertificateId("123456789012345678");
		info.setCustomerName("customerName");
		
		info.setRiskType("");
		info.setRiskValue(0);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("境外标志: " + info.getForeignFlag(), info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setForeignFlag(true);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(20 == info.getRiskValue());
		assertEquals("境外标志: " + info.getForeignFlag(),  info.getRiskType());
		
		info.setRiskType("");
		info.setRiskValue(0);
		info.setForeignFlag(false);
		thresholdService.setCustomerThresholdsInfo(info);
		assertTrue(0 == info.getRiskValue());
		assertEquals("", info.getRiskType());
	}
}

package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.elulian.CustomerSecurityManagementSystem.dao.DAOFactory;
import com.elulian.CustomerSecurityManagementSystem.dao.IRiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.dao.IUserInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.ExistsException;
import com.elulian.CustomerSecurityManagementSystem.exception.UserExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.impl.RiskRankService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;
import com.elulian.CustomerSecurityManagementSystem.vo.Role;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import jxl.common.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {//"classpath:**/applicationContext-resources.xml",
		// "classpath:applicationContext-dao.xml",
		// "classpath:applicationContext-service.xml",
		"classpath:**/security.xml", "classpath:**/applicationContext*.xml" })
/*
 * @TransactionConfiguration(transactionManager="transactionManager",defaultRollback
 * =true)
 * 
 * @Transactional
 */
public class RiskRankServiceTest {

	private final Logger logger = Logger.getLogger(getClass());

	/* @Autowired */
	private RiskRankService riskRankService;

	private IRiskRankDAO riskRankDAO;

	private Mockery context;

	private RiskRank riskRank;

	@Before
	public void setUp() {

		context = new Mockery();
		/* need to consider security testing in service layer */
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("Admin", "password"));

		riskRankDAO = context.mock(IRiskRankDAO.class);

		riskRankService = new RiskRankService();

		riskRankService.setRiskRankDAO(riskRankDAO);

		// final IRiskRankService
	}

	@Test
	public void testAddRiskRankService() {

		riskRank = new RiskRank();

		RiskRank riskRankSaved = null;

		context.checking(new Expectations() {
			{
				oneOf(riskRankDAO).findAll();
				will(returnValue(new ArrayList<RiskRank>()));
				oneOf(riskRankDAO).save(riskRank);
				will(returnValue(riskRank));
			}
		});

		riskRankSaved = riskRankService.save(riskRank);

		context.assertIsSatisfied();
		assertSame("should be loaded object", riskRank, riskRankSaved);
	}

}

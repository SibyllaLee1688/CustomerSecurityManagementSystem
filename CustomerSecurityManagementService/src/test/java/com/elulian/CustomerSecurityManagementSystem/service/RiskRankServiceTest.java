package com.elulian.CustomerSecurityManagementSystem.service;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.elulian.CustomerSecurityManagementSystem.dao.IRiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.service.impl.RiskRankService;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

public class RiskRankServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RiskRankServiceTest.class);

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

	}

	@Test
	public void testAddRiskRankService() {

		logger.info("testAddRiskRankService without overlap");
		riskRank = new RiskRank();

		RiskRank riskRankSaved = null;

		final List<RiskRank> list = new ArrayList<RiskRank>();
		
		context.checking(new Expectations() {
			{
				oneOf(riskRankDAO).findAll();
				will(returnValue(list));
				oneOf(riskRankDAO).save(riskRank);
				will(returnValue(riskRank));
			}
		});

		riskRankSaved = riskRankService.save(riskRank);

		context.assertIsSatisfied();
		assertSame("riskRankSaved: " + riskRankSaved.toString() + " riskRank: " + riskRank.toString(), riskRank, riskRankSaved);
		
		logger.info("testAddRiskRankService with overlap");
		
		riskRank = new RiskRank();
		
		riskRank.setMinValue(10);
		riskRank.setMaxValue(100);

		riskRankSaved = null;

		list.add(new RiskRank(0, "test", 0, 10));
		
		context.checking(new Expectations() {
			{
				oneOf(riskRankDAO).findAll();
				will(returnValue(list));
			}
		});

		riskRankSaved = riskRankService.save(riskRank);

		context.assertIsSatisfied();
		assertSame("riskRankSaved should be null", riskRankSaved, null);

	}

}

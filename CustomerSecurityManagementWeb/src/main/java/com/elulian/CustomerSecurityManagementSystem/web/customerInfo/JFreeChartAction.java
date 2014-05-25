package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.elulian.CustomerSecurityManagementSystem.service.ICustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.IRiskRankService;
import com.elulian.CustomerSecurityManagementSystem.service.impl.CustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.impl.RiskRankService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

import com.opensymphony.xwork2.ActionSupport;

@Controller("jfreeChartAction") @Scope("prototype")
public class JFreeChartAction extends ActionSupport implements SessionAware{

	//ChartResult will call this to create image->ActionInvocation.getStack().findValue("chart")
	private JFreeChart chart;

	@Autowired
	private ICustomerInfoService customerInfoService;
	
	@Autowired
	private IRiskRankService riskRankService;

	private Condition condition;
	
	private List<RiskRank> riskRanklist;

	private Map<String,Object> sessionMap;

	public JFreeChartAction() {
		/*if (customerInfoService == null)
			customerInfoService = new CustomerInfoService();
		if (riskRanklist == null)
			riskRanklist = new RiskRankService().findAll();
			*/
	}

	public String chart() {
		//get data from database
		if (condition == null){
			condition = new Condition();
		} 
		
		if (riskRanklist == null)
			riskRanklist = riskRankService.findAll();
		
		// get user branch
		String branch = (String)sessionMap.get("branch");
		// set user branch if user branch is not ALL
		// normal user can only see the customerInfos in his/her branch
		if(branch != null && (!branch.equalsIgnoreCase("ALL"))){
			condition.setSearchBranch(branch);
		}
		long total = customerInfoService.getTotalCount(condition);
		List<Object> args = new ArrayList<Object>();
		args.add(total);
		boolean flag = false;
		Font font = new Font("宋体", Font.BOLD, 16);
		DefaultPieDataset data = new DefaultPieDataset();
		//        Condition con = new Condition(condition);
		Integer defaultMinValue = condition.getRiskMinValue();
		Integer defaultMaxValue = condition.getRiskMaxValue();
		for (RiskRank riskRank : riskRanklist) {
			if (condition.getRiskMinValue() == null
					|| condition.getRiskMinValue() < riskRank.getMinValue()) {
				condition.setRiskMinValue(riskRank.getMinValue());
				flag = true;
			}
			if (condition.getRiskMaxValue() == null
					|| condition.getRiskMaxValue() > riskRank.getMaxValue()) {
				condition.setRiskMaxValue(riskRank.getMaxValue());
				flag = true;
			}
			long count = customerInfoService.getTotalCount(condition);
			if (count != 0)
				data.setValue(riskRank.getRankType() + ": " + count, count);
			condition.setRiskMaxValue(defaultMaxValue);
			condition.setRiskMinValue(defaultMinValue);
			if (!flag)
				break;
		}
		//create JFreeChart object
		chart = ChartFactory.createPieChart3D(getText("image.title", args), data, true, true,
				Locale.CHINA);
		//Set title to Chinese
		chart.getTitle().setFont(font);
		//Set plot to Chinese
		((PiePlot)chart.getPlot()).setLabelFont(font);
		//Set Legend to Chinese
		chart.getLegend().setItemFont(font);
		return SUCCESS;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public void setSession(Map<String,Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
}

package com.elulian.CustomerSecurityManagementSystem.web.admin.riskRank;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;


import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.ExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IRiskRankService;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("riskRankAction") @Scope("prototype")
public class RiskRankAction extends ActionSupport {

	private static Logger logger = Logger.getLogger(RiskRankAction.class);
	
	@Autowired
	private IRiskRankService riskRankService;
	
	private List<RiskRank> riskRanks;
	private RiskRank riskRank;
	private Integer id;
	
	/*
	protected IRiskRankService getIRiskRankService(){
		if(service == null)
			service = ServiceFactory.getServiceFactory().getIRiskRankService();
		return service;
	}
	*/

	public List<RiskRank> getRiskRanks() {
		return riskRanks;
	}

	public RiskRank getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(RiskRank riskRank) {
		this.riskRank = riskRank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String list(){
		this.riskRanks = riskRankService.findAll();
		return Action.SUCCESS;
	}
	/*
	public String execute() {		
		return Action.SUCCESS;
	}*/

	public String save() {
		logger.debug(riskRank.getRankType());
		if (riskRank.getMinValue() > riskRank.getMaxValue()){
			ActionContext.getContext().put("tip", "Max Value should >= Min Value!");
			// return list();
		} else if(riskRankService.isOverlapRank(riskRank)) {
			ActionContext.getContext().put("tip", "The riskRank is overlap with another riskRank");
		} else {
			try {
				this.riskRankService.save(riskRank);
			} catch (DataIntegrityViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataAccessApiUsageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.riskRank = new RiskRank();
		}
		return list();
	}

	public String remove() {
		this.riskRankService.remove(id);
		return list();
	}

    public void prepare() throws Exception {
        if (id != null)
        	this.riskRank = this.riskRankService.findById(id);
    }

	
}

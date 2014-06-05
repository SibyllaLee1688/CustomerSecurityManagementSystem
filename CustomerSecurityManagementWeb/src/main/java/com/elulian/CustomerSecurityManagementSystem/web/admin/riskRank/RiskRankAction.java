package com.elulian.CustomerSecurityManagementSystem.web.admin.riskRank;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;

import com.elulian.CustomerSecurityManagementSystem.service.IRiskRankService;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@Controller("riskRankAction") @Scope("prototype")
public class RiskRankAction extends ActionSupport implements Preparable{

	private static Logger logger = LoggerFactory.getLogger(RiskRankAction.class);
	
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
		logger.debug(riskRank.toString());
		if (riskRank.getMinValue() > riskRank.getMaxValue()){
			ActionContext.getContext().put("tip", "Max Value should >= Min Value!");
			// return list();
		} else if(riskRankService.isOverlapRank(riskRank)) {
			ActionContext.getContext().put("tip", "The riskRank is overlap with another riskRank");
		} else {
			try {
				this.riskRankService.save(riskRank);
			} catch (DataIntegrityViolationException e) {
				logger.error(e.getMessage(), e);
			} catch (InvalidDataAccessApiUsageException e) {
				logger.error(e.getMessage(), e);
			}
			//this.riskRank = new RiskRank();
		}
		return list();
	}

	public String remove() {
		this.riskRankService.remove(id);
		return list();
	}
	
	/**
	 * prepare riskrank before edit execution
	 */
	public void prepare() throws Exception {
		/*
		 * parameters is not interceptor before perpare, so 
		 * directly get userInfo.id from request parameter 
		 * With this implementation
		 * Note: version field should be kept in page for Optimistic Locking
		 * or reload the object will overwrite the old version in page
		 * side  
		 */
		if ( ServletActionContext.getRequest().getMethod().equalsIgnoreCase("post")){
			String inputIdString = ServletActionContext.getRequest().getParameter("riskRank.id");
			/* just for integer >= 0 */
			if((inputIdString.matches("^\\d+$"))) {
				riskRank = this.riskRankService.findById(Integer.parseInt(inputIdString));
			}
		}
	}
}

package com.elulian.CustomerSecurityManagementSystem.service.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elulian.CustomerSecurityManagementSystem.dao.IRiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.service.IRiskRankService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

@Service("riskRankService")
@RolesAllowed({"ROLE_ADMIN"})
public class RiskRankService extends BaseService<RiskRank, Integer> implements IRiskRankService {

	private IRiskRankDAO riskRankDAO;
	
	/*protected IRiskRankDAO getRiskRankDAO(){
		if(riskRankDAO == null){
			riskRankDAO = DAOFactory.getDAOFacotry().getIRiskRankDAO();
		}
		return riskRankDAO;
	}*/
	
	@Autowired
	public void setRiskRankDAO(IRiskRankDAO riskRankDAO) {
		this.dao = riskRankDAO;
		this.riskRankDAO = riskRankDAO;
	}

	public boolean isOverlapRank(RiskRank riskRank) {

		List<RiskRank> list = findAll();

		if (null != list) {

			for (RiskRank entity : list) {
				if (null != riskRank.getId()
						&& riskRank.getId().equals(entity.getId()))
					continue;
				/* default riskRan Max >= Min */
				if ((riskRank.getMaxValue() >= entity.getMinValue())
						&& (riskRank.getMinValue() <= entity.getMaxValue())) {
					return true;
				}
			}
			return false;
		}
		
		logger.error("unexpected null list from dao layer for RiskRank's findAll function");
		
		return false;
	}
	
	@Override
	public RiskRank save(RiskRank riskRank){
		if(!isOverlapRank(riskRank))
			return super.save(riskRank);
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		RiskRank riskRank = new RiskRank();
		riskRank.setMaxValue(999);
		riskRank.setMinValue(100);
		riskRank.setRankType("H");
		ServiceFactory.getServiceFactory().getIRiskRankService().save(riskRank);
	}
}

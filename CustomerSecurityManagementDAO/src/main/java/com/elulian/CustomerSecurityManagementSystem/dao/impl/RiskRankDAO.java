package com.elulian.CustomerSecurityManagementSystem.dao.impl;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IRiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

@Repository("riskRankDAO")
public class RiskRankDAO extends BaseDAO<RiskRank, Integer> implements IRiskRankDAO {
/*
	@Override
	public boolean isOverlapRank(RiskRank riskRank) {
		for(RiskRank entity : findAll()){
			if(riskRank.getId() != null && riskRank.getId().equals(entity.getId()))
				continue;
			if((riskRank.getMaxValue() >= entity.getMinValue()) && (riskRank.getMinValue() <= entity.getMaxValue())){
				return true;
			}
		}
		return false;
	}
*/

}

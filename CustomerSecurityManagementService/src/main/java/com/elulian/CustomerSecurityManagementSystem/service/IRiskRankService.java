package com.elulian.CustomerSecurityManagementSystem.service;

import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

public interface IRiskRankService extends IBaseService<RiskRank, Integer> {
		
	public boolean isOverlapRank(RiskRank riskRank);
}

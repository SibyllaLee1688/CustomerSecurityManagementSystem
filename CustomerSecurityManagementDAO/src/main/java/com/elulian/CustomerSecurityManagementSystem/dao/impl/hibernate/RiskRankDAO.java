/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.hibernate;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IRiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.RiskRank;

/**
 * @author elulian
 *
 */
@Repository("riskRankDAO")
public class RiskRankDAO extends BaseDAO<RiskRank, Integer> implements IRiskRankDAO {

}

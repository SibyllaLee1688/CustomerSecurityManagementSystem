/**
 *  * 
 * @deprecated replaced by drool rule engine, rule engine can 
 * 				enable new rules without recompile the system
 * 
 * @author elulian
 *
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IThresholdDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

@Deprecated
@Repository("thresholdDAO")
public class ThresholdDAO extends BaseDAO<Threshold, Integer> implements IThresholdDAO {

}

/**
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.dao.impl.hibernate;

import org.springframework.stereotype.Repository;

import com.elulian.CustomerSecurityManagementSystem.dao.IThresholdDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

/**
 * @author elulian
 *
 */
@Deprecated
@Repository("thresholdDAO")
public class ThresholdDAO extends BaseDAO<Threshold, Integer> implements IThresholdDAO {

}

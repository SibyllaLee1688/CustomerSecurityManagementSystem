package com.elulian.CustomerSecurityManagementSystem.dao;

import com.elulian.CustomerSecurityManagementSystem.dao.impl.CustomerInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.dao.impl.RiskRankDAO;
import com.elulian.CustomerSecurityManagementSystem.dao.impl.ThresholdDAO;
import com.elulian.CustomerSecurityManagementSystem.dao.impl.UserInfoDAO;

/**
 * 
 * @deprecated replaced by spring ioc
 * 
 * @author elulian
 *
 */
@Deprecated
public class DAOFactory {

	private static DAOFactory factory;

	private IThresholdDAO thresholdDAO;

	private IUserInfoDAO userInfoDAO;

	private IRiskRankDAO riskRankDAO;

	private ICustomerInfoDAO customerInfoDAO;

	private DAOFactory() {

	}

	public static DAOFactory getDAOFacotry() {
		if (factory == null) {
			synchronized (DAOFactory.class) {
				if (factory == null)
					factory = new DAOFactory();
			}
		}
		return factory;
	}

	public IThresholdDAO getIThresholdDAO() {
		if (thresholdDAO == null) {
			synchronized (this) {
				if (thresholdDAO == null)
					thresholdDAO = new ThresholdDAO();
			}
		}
		return thresholdDAO;
	}

	public IUserInfoDAO getIUserInfoDAO() {
		if (userInfoDAO == null) {
			synchronized (this) {
				if (userInfoDAO == null)
					userInfoDAO = new UserInfoDAO();
			}
		}
		return userInfoDAO;
	}

	public IRiskRankDAO getIRiskRankDAO() {
		if (riskRankDAO == null) {
			synchronized (this) {
				if (riskRankDAO == null)
					riskRankDAO = new RiskRankDAO();
			}
		}
		return riskRankDAO;
	}

	public ICustomerInfoDAO getICustomerInfoDAO() {
		if (customerInfoDAO == null) {
			synchronized (this) {
				if (customerInfoDAO == null)
					customerInfoDAO = new CustomerInfoDAO();
			}
		}
		return customerInfoDAO;
	}

}

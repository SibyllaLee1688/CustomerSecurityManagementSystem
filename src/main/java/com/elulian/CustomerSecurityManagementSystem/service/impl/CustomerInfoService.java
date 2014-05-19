package com.elulian.CustomerSecurityManagementSystem.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elulian.CustomerSecurityManagementSystem.dao.ICustomerInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.service.ICustomerInfoService;
import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

@Service("customerInfoService")
public class CustomerInfoService extends BaseService<CustomerInfo, Integer> implements ICustomerInfoService {
	
	private ICustomerInfoDAO customerInfoDAO;
	
	
	private IThresholdService thresholdService;

/*	public ICustomerInfoDAO getICustomerInfoDAO() {
		if(customerInfoDAO == null)
			customerInfoDAO = getDAOFacotry().getICustomerInfoDAO();
		return customerInfoDAO;
	}*/


	@Autowired
	public void setCustomerInfoDAO(ICustomerInfoDAO customerInfoDAO) {
		this.dao = customerInfoDAO; 
		this.customerInfoDAO = customerInfoDAO;
	}

	@Override
	@RolesAllowed({"ROLE_USER","ROLE_ADMIN"}) 
	public List<CustomerInfo> getCustomerInfosByCondition(Condition condition) {
		return customerInfoDAO.getCustomerInfoByCondition(condition);
	}
	
	@Override
	@RolesAllowed({"ROLE_USER","ROLE_ADMIN"}) 
	public long getTotalCount(Condition condition) {
		return customerInfoDAO.getTotalCount(condition);
	}

	@Override
	@RolesAllowed({"ROLE_USER","ROLE_ADMIN"}) 
	public CustomerInfo findById(Integer id) {
		return super.findById(id);
	}

	@Override
	@RolesAllowed({"ROLE_USER","ROLE_ADMIN"}) 
	public boolean exists(Integer id) {
		return super.exists(id);
	}

	@Override
	@RolesAllowed({"ROLE_ADMIN"})
	public void setMatchThresholdsInfo(CustomerInfo info) {
		thresholdService.setCustomerThresholdsInfo(info);
		// TODO Auto-generated method stub
		/*
		 * info.setRiskValue(0);
		info.setRiskType(null);
		String temp = info.getProfessionCode();
		List<Threshold> list = thresholdService.findAll();
		// boolean flag = false;
		if (temp == null || temp.equalsIgnoreCase("����")
				|| temp.equalsIgnoreCase("��ҵ")) {
			info.setRiskValue(info.getRiskValue() + 20);
			info.setRiskType(list.get(0).getType());
		}
		if (info.getCertificateEndDate() == null
				|| info.getCertificateEndDate().before(new Date())) {
			info.setRiskValue(info.getRiskValue() + 80);
			if (info.getRiskType() != null)
				info.setRiskType(info.getRiskType() + ", "
						+ list.get(1).getType());
			else
				info.setRiskType(list.get(1).getType());
		}
		if (info.getCustomerName() == null) {
			info.setRiskValue(info.getRiskValue() + 80);
			if (info.getRiskType() != null)
				info.setRiskType(info.getRiskType() + ", "
						+ list.get(2).getType());
			else
				info.setRiskType(list.get(2).getType());
		}
		if (info.getCertificateId() == null) {
			info.setRiskValue(info.getRiskValue() + 80);
			if (info.getRiskType() != null)
				info.setRiskType(info.getRiskType() + ", "
						+ list.get(3).getType());
			else
				info.setRiskType(list.get(3).getType());
		}
		if (info.isForeignFlag()) {
			info.setRiskValue(info.getRiskValue() + 20);
			if (info.getRiskType() != null)
				info.setRiskType(info.getRiskType() + ", "
						+ list.get(4).getType());
			else
				info.setRiskType(list.get(4).getType());
		}*/
		/*
		 * if(info.getRiskType().startsWith("��")){
		 * info.setRiskType(info.getRiskType
		 * ().substring(info.getRiskType().indexOf("��"))); }
		 */
	}

	/*
	 * only admin can modify customer data
	 * @RolesAllowed({"ROLE_ADMIN"}) 
	@Override
	public CustomerInfo save(CustomerInfo object) {
		return super.save(object);
	}

	@Override
	public void remove(CustomerInfo object) {
		// TODO Auto-generated method stub
		super.remove(object);
	}

	@Override
	public void remove(Integer id) {
		// TODO Auto-generated method stub
		super.remove(id);
	}
	*/
}

package com.elulian.CustomerSecurityManagementSystem.service.impl;

import java.util.Date;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elulian.CustomerSecurityManagementSystem.dao.DAOFactory;
import com.elulian.CustomerSecurityManagementSystem.dao.IThresholdDAO;
import com.elulian.CustomerSecurityManagementSystem.exception.NotExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

@Service("thresholdService")
//@RolesAllowed({"ROLE_ADMIN"})
public class ThresholdService implements
		IThresholdService {

	private KieServices ks = null;
	
	private StatelessKieSession kSession = null;
	
	private KieContainer kContainer = null;
	
	@Deprecated
	private IThresholdDAO thresholdDAO;

	@Autowired
	public void setThresholdDAO(IThresholdDAO thresholdDAO) {
		this.thresholdDAO = thresholdDAO;
	}

	@Deprecated
	protected IThresholdDAO getThresholdDAO() {
		// synchoronized is not required here, dao factory's responsibility to
		// keep only one instance.
		
		this.thresholdDAO = DAOFactory.getDAOFacotry().getIThresholdDAO();
		return thresholdDAO;
	}
	
	

	@Override
	@Deprecated
	public void edit(Threshold threshold) {
		thresholdDAO.save(threshold);
	}

	@Deprecated
	/**
	 * 1. We can't control the sequence in database.
	 * 2. For performance, we should get threshold in KHXXController directly
	 */
	public void setMatchThresholdsInfo(CustomerInfo info) {
		String temp = info.getProfessionCode();
		// boolean flag = false;
		if (null == temp || 0 == (temp.trim()).length() || "其他".equals(temp) || "其它".equals(temp) || "无业".equals(temp) || "未知".equals(temp)) {
			info.setRiskValue(info.getRiskValue() + 20);
		    info.setRiskType(info.getRiskType() + " Invalid professionCode value: " + info.getProfessionCode());
		}
		if (info.getCertificateEndDate() == null
				|| info.getCertificateEndDate().before(new Date())) {
			 info.setRiskValue(info.getRiskValue() + 80);
		     info.setRiskType(info.getRiskType() + " Invalid Certification End date value: " + info.getCertificateEndDate());
		}
		if (info.getCustomerName() == null || info.getCustomerName().trim().length() == 0) {
			info.setRiskValue(info.getRiskValue() + 80);
			info.setRiskType(info.getRiskType() + " Customer Name is null");
		}
		if (info.getCertificateId() == null || 0 == (info.getCertificateId().trim()).length()) {
			info.setRiskValue(info.getRiskValue() + 80);
			 info.setRiskType(info.getRiskType() + " Invalid Certification ID: " + info.getCertificateId());
		}
		if (null == info.isForeignFlag() ||info.isForeignFlag()) {
			info.setRiskValue(info.getRiskValue() + 20);
			info.setRiskType(info.getRiskType() + " 境外标志: " + info.getForeignFlag());
		}
		
		info.setRiskType(info.getRiskType().trim());
	}

	/**
	 * Since Stateful Knowledge Session is the most commonly used session
	 * type it is just named KieSession in the KIE API
	 * Stateless Sessions typically do not use inference, so the
	 * engine does not need to be aware of changes to data. Inference can also be turned off explicitly
	 * by using the sequential mode.
	 * stateless kie session is used here, no inference required.
	 */
	@Override
	public void initThresholdService(String groupId, String artifactId, String version)  throws NotExistsException {
		ks = KieServices.Factory.get();
		try{
			kContainer = ks.newKieContainer(ks.newReleaseId(groupId, artifactId, version));
		} catch (Exception e){
			StringBuilder sb = new StringBuilder("fail to get maven rules kmodule from groupId: [");
			sb.append(groupId).append("] artifactId: [").append(artifactId).append("] version: [").append(version).append("]");
			throw new NotExistsException(sb.toString());
		}
		if(null != kContainer)
			kSession = kContainer.newStatelessKieSession("ksession-rules");
	}

	@Override
	public void refreshThresholdService(String groupId, String artifactId, String version) throws NotExistsException {
		try{
			kContainer.updateToVersion(ks.newReleaseId(groupId, artifactId, version));
		} catch (Exception e){
			StringBuilder sb = new StringBuilder("fail to get maven rules kmodule from groupId: [");
			sb.append(groupId).append("] artifactId: [").append(artifactId).append("] version: [").append(version).append("]");
			throw new NotExistsException(sb.toString());
		}
		if(null != kContainer)
			kSession = kContainer.newStatelessKieSession("ksession-rules");
	}

	@Override
	public void setCustomerThresholdsInfo(CustomerInfo info) {
		kSession.execute(info);
	}

}

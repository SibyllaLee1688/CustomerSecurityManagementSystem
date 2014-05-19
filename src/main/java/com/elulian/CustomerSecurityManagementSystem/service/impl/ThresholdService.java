package com.elulian.CustomerSecurityManagementSystem.service.impl;

import java.util.Date;
import java.util.List;

import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elulian.CustomerSecurityManagementSystem.dao.DAOFactory;
import com.elulian.CustomerSecurityManagementSystem.dao.IThresholdDAO;
import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

@Service("thresholdService")
//@RolesAllowed({"ROLE_ADMIN"})
public class ThresholdService implements
		IThresholdService {

	private KieServices ks = null;
	
	private StatelessKieSession kSession = null;
	
	
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
		info.setRiskValue(0);
		info.setRiskType(null);
		String temp = info.getProfessionCode();
		List<Threshold> list = getThresholdDAO().findAll();
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
		}
		/*
		 * if(info.getRiskType().startsWith("��")){
		 * info.setRiskType(info.getRiskType
		 * ().substring(info.getRiskType().indexOf("��"))); }
		 */
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
	public void initThresholdService() {
		ks = KieServices.Factory.get();
		startThresholdService();
	}
	
	private void startThresholdService(){
		KieContainer kContainer = ks.getKieClasspathContainer();
		kSession = kContainer.newStatelessKieSession("ksession-rules");
		kSession.addEventListener( new DebugAgendaEventListener() );
       // kSession.addEventListener( new DebugWorkingMemoryEventListener() );
	}

	@Override
	public void refreshThresholdService() {
		startThresholdService();
	}

	@Override
	public void setCustomerThresholdsInfo(CustomerInfo info) {
		kSession.execute(info);
	}

	@Override
	public boolean editThresholdRulesFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateThresholdRulesFile() {
		// TODO Auto-generated method stub
		return false;
	}

}
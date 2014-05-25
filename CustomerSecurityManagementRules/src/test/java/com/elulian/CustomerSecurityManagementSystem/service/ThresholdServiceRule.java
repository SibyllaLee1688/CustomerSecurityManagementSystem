package com.elulian.CustomerSecurityManagementSystem.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

public class ThresholdServiceRule {

	private KieServices ks = null;
	
	private StatelessKieSession kSession = null;
	
	private KieContainer kContainer = null;

	/**
	 * Since Stateful Knowledge Session is the most commonly used session
	 * type it is just named KieSession in the KIE API
	 * Stateless Sessions typically do not use inference, so the
	 * engine does not need to be aware of changes to data. Inference can also be turned off explicitly
	 * by using the sequential mode.
	 * stateless kie session is used here, no inference required.
	 */

	public void initThresholdService() {
		ks = KieServices.Factory.get();
		kContainer = ks.getKieClasspathContainer();
    	kSession = kContainer.newStatelessKieSession("ksession-rules");
	}

	public void setCustomerThresholdsInfo(CustomerInfo info) {
		kSession.execute(info);
	}

}

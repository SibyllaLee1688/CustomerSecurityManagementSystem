/**
 * 
 * This threshold action service provide upload new drl file, apply new drl service,
 * list the context of drl file, for more detail drl modification, we use another
 * web application provided by drools: docs.jboss.org/drools/release/5.6.0.Final/drools-guvnor-docs/html_single/index.html
 * 	
 * 
 * @author elulian
 * 
 */
package com.elulian.CustomerSecurityManagementSystem.web.admin.threshold;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.ExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.Threshold;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

@Controller("thresholdAction")
@Scope("prototype")
public class ThresholdAction extends ActionSupport {

	@Autowired
	private IThresholdService thresholdService;
	private List<Threshold> thresholds;
	private Threshold threshold;
	private Integer id;

	public Threshold getThreshold() {
		return threshold;
	}

	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ThresholdAction() {
		/*
		 * if (thresholdService == null) thresholdService =
		 * ServiceFactory.getServiceFactory().getIThresholdService();
		 */
	}

	public String list() {
		// this.thresholds = thresholdService.findAll();
		return Action.SUCCESS;
	}

	/*
	 * public String execute() { return Action.SUCCESS; }
	 */

	public String save() {
		try {
			// this.thresholdService.save(threshold);
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataAccessApiUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.threshold = new Threshold();
		return list();
	}

	public String remove() {
		// this.thresholdService.remove(id);
		return list();
	}

	public List<Threshold> getThresholds() {
		return thresholds;
	}

	public void prepare() throws Exception {
		/*
		 * if (id != null) threshold = thresholdService.findById(id);
		 */
	}

}

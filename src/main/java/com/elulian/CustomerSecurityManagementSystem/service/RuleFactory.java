package com.elulian.CustomerSecurityManagementSystem.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;


import com.elulian.CustomerSecurityManagementSystem.common.ConfigServlet;
import com.elulian.CustomerSecurityManagementSystem.exception.RuleDataIDExistException;
import com.elulian.CustomerSecurityManagementSystem.exception.RuleDataIDNotExistException;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

/**
 * Solution A: Keep a rule list in ruleFactory benefit: performance is better,
 * don't need to read file every time. weakness: need to synchronize List and
 * xml configuration file, may have transaction problem.
 * 
 * Solution B: Read rule list from xml file every time benefit: won't need to
 * synchronize list and xml file, just xml file is ok. weakness: performance is
 * not good, should read file when file is changed. List also should be updated
 * after file is changed.
 * 
 * Final Solution in this case: B. Keep it simple. :-) Performance is not so
 * important in this situation.
 * 
 * @author cloudlu
 * 
 */
public class RuleFactory {

	private static Logger logger = Logger.getLogger(RuleFactory.class);

	private XMLConfiguration config;

//	private List<Rule> rules;

	private static RuleFactory factory = new RuleFactory();

	public static RuleFactory getInstance() {
		return factory;
	}

	private RuleFactory() {
		try {
			config = new XMLConfiguration(ConfigServlet.class
					.getResource("/thresholds.xml"));
			// not required for rules, added pages in web applications to update
			// this.
			// config.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e) {
			logger.error(logger.getName() + "********* Can't find Config file",
					e);
			System.exit(-1);
		}
	}

	private Rule convertElementToRule(HierarchicalConfiguration con) {
		Integer id = con.getInt("id");
		String attribute = con.getString("attribute");
		String type = con.getString("type");
		String condition = con.getString("condition");
		//@SuppressWarnings("rawtypes")
		List params = con.getList("params.param");
		Integer value = con.getInt("value");
		String description = con.getString("description");
		logger.debug(logger.getName()
				+ "***************** convertElementToRule");
		logger.debug(attribute + type + condition + value + description);
		/*
		 * for(Object param : params){ if(param instanceof Number){
		 * logger.debug((Number)param); logger.debug("This value is a Number");
		 * } else if (param instanceof Boolean){ logger.debug((Boolean)param);
		 * logger.debug("This value is a Boolean"); } else if (param instanceof
		 * Date){ logger.debug((Date)param);
		 * logger.debug("This value is a Date"); } else if (param instanceof
		 * String){ logger.debug((String)param);
		 * logger.debug("This value is a string"); } else { logger.debug(param);
		 * logger.debug("This value is unknow"); } }
		 */
		return new Rule(new RuleData(id, attribute, type, condition, params,
				value, description));
	}

	/**
	 * Add a new Rule in the system, also update the RuleData in system.
	 * 
	 * @param rule
	 *            The rule need to be added in the system
	 * @throws RuleDataIDExistException
	 *             If the rule data's id is not exist in the system.
	 * @throws ConfigurationException
	 *             If the configuration file is not exist
	 */
	public void addRule(Rule rule) throws RuleDataIDExistException,
			ConfigurationException {

		/*
		 * boolean existFlag = false;
		 * 
		 * for (Rule r : rules) { if (rule.getData().getId() ==
		 * r.getData().getId()) { existFlag = true; } } if (!existFlag) {
		 * rules.add(rule); addRuleData(rule.getData()); } else throw new
		 * RuleDataIDExistException("RuleData ID: " + rule.getData().getId() +
		 * " is already used by another Rule");
		 */
		synchronized (this) {
			for (Object id : config.getList("threshold.id")) {
				if (rule.getData().getId() == Integer.parseInt((String) id)) {
					throw new RuleDataIDExistException("RuleData ID: "
							+ rule.getData().getId()
							+ " is already used by another Rule");
				}
			}
			addRuleData(rule.getData());
//			constructRules();
		}

	}

	/**
	 * RuleData Id and value are required. Should check this.
	 * 
	 * @param data
	 * @throws ConfigurationException
	 */
	private void addRuleData(RuleData data) throws ConfigurationException {
		assert data != null : "RuleData which need to be added should not be null!";
		logger.debug(logger.getName() + "***************** addRuleData");
		logger.debug("Add threshold with Id: " + data.getId()
				+ " in Config file");
		// config.addProperty("threshold(-1)", null);
		synchronized (config) {
			config.addProperty("threshold(-1).id", data.getId());
			config.addProperty("threshold.condition", data.getCondition());
			config.addProperty("threshold.value", data.getValue());
			config.addProperty("threshold.description", data.getDescription());
			if (data.getParams() != null) {
				for (int j = 0; j < data.getParams().size(); j++) {
					config.addProperty("threshold.params.param(" + j + ")",
							data.getParams().get(j));
				}
			}
			saveConfig();
		}
	}

	/**
	 * Update an existing rule's data in system, also update the RuleData in
	 * system.
	 * 
	 * @param rule
	 *            The rule whose data need to update
	 * @throws RuleDataIDNotExistException
	 *             If ID in rule data is not existing in system.
	 * @throws ConfigurationException
	 *             If the configuration file is not exist.
	 */
	public void updateRule(Rule rule) throws RuleDataIDNotExistException,
			ConfigurationException {
/*		boolean updateFlag = false;
		int updateIndex = 0;

		for (Rule r : rules) {
			if (rule.getData().getId() == r.getData().getId()) {
				updateFlag = true;
			}
			if (!updateFlag)
				updateIndex++;
		}
		if (updateFlag) {
			rules.remove(updateIndex);
			rules.add(updateIndex, rule);
			updateRuleData(rule.getData());
		} else
			throw new RuleDataIDNotExistException("RuleData ID: "
					+ rule.getData().getId() + " is not exist for update");
		*/
		synchronized (this) {
			for (Object id : config.getList("threshold.id")) {
				if (rule.getData().getId() == Integer.parseInt((String) id)) {
					updateRuleData(rule.getData());
//					constructRules();
					return;
				}
			}
			throw new RuleDataIDNotExistException("RuleData ID: "
					+ rule.getData().getId() + " is not exist for update");
		}

	}

	/**
	 * Update rule data in system, won't check whether the data is existing in
	 * the system. It should be checked by the caller. The caller to this method
	 * should make sure the rule data's id is existing in the system.
	 * 
	 * @param data
	 *            RuleData need to be updated in system.
	 * @throws ConfigurationException
	 */
	private void updateRuleData(RuleData data) throws ConfigurationException {
		assert data != null : "RuleData which need to be updated should not be null!";

		HierarchicalConfiguration sub;
		// loop all threshold
		for (int i = 0; i < config.getList("threshold.id").size(); i++) {
			sub = config.configurationAt("threshold(" + i + ")");
			// find the exactly node by Id
			if (sub.getInt("id") == data.getId()) {
				synchronized (this) {
					// update all data
					sub.setProperty("attribute", data.getAttribute());
					sub.setProperty("type", data.getType());
					sub.setProperty("condition", data.getCondition());
					// Params is a list, should clear then add
					sub.clearProperty("params.param");
					if (data.getParams() != null) {
						for (int j = 0; j < data.getParams().size(); j++) {
							sub.addProperty("params.param(" + j + ")", data
									.getParams().get(j));
						}
					}
					sub.setProperty("value", data.getValue());
					sub.setProperty("description", data.getDescription());
					saveConfig();
				}
			}
		}
	}

	/**
	 * Remove a rule in system, also remove the rule data
	 * 
	 * @param rule
	 *            The rule need to be removed
	 * @throws RuleDataIDNotExistException
	 *             If the rule data's id is not exist in the system.
	 * @throws ConfigurationException
	 *             If the configuration file is not exist.
	 */
	public void removeRule(Rule rule) throws RuleDataIDNotExistException,
			ConfigurationException {
	/*	boolean removeFlag = false;
		int removeIndex = 0;

		for (Rule r : rules) {
			if (rule.getData().getId() == r.getData().getId()) {
				removeFlag = true;
			}
			if (!removeFlag)
				removeIndex++;
		}
		if (removeFlag) {
			rules.remove(removeIndex);
			removeRuleData(rule.getData());
		} else
			throw new RuleDataIDNotExistException("RuleData ID: "
					+ rule.getData().getId() + " is not exist for update");*/
		
		synchronized (this) {
			for (Object id : config.getList("threshold.id")) {
				if (rule.getData().getId() == Integer.parseInt((String) id)) {
					removeRuleData(rule.getData());
//					constructRules();
					return;
				}
			}
			throw new RuleDataIDNotExistException("RuleData ID: "
					+ rule.getData().getId() + " is not exist for update");
		}
	}

	/**
	 * Remove the rule data in system, won't check whether the data is existing
	 * in the system. It should be checked by the caller.
	 * 
	 * @param data
	 *            The rule data need to be removed.
	 * @throws ConfigurationException
	 */
	private void removeRuleData(RuleData data) throws ConfigurationException {
		assert data != null : "RuleData which need to be removed should not be null!";

		HierarchicalConfiguration sub;
		for (int i = 0; i < config.getList("threshold.id").size(); i++) {
			sub = config.configurationAt("threshold(" + i + ")");
			if (sub.getInt("id") == data.getId()) {
				logger.debug(logger.getName()
						+ "***************** removeRuleData");
				logger.debug("Remove threshold with Id: " + data.getId()
						+ " in Config file");
				synchronized (this) {
					sub.clear();
					saveConfig();
				}
			}
		}
	}

	/**
	 * Save the configuration file
	 * 
	 * @throws ConfigurationException
	 *             If the configuration save action is failed.
	 */
	private void saveConfig() throws ConfigurationException {
		try {
			config.save();
		} catch (ConfigurationException e) {
			logger.error(logger.getName() + "********* Can't find Config file",
					e);
			throw new ConfigurationException("Can't find the config file", e);
		}
	}

	/**
	 * Get rule list from xml file
	 * Operation on the return list won't impact xml database unless you use add/Remove/Update Rule command. 
	 * @return ArrayList contains all rules
	 */
	public List<Rule> getRules() {
		return constructRules();
	}

	/**
	 * Read Rules from xml
	 * Every time return a stand alone list to prevent reference modification
	 * Another solution: Return a copy of the list by implement clone method. Consider this later. 
	 * @return
	 */
	private List<Rule> constructRules() {
		List<Rule> rules = new ArrayList<Rule>();
		HierarchicalConfiguration sub;
		logger.debug(logger.getName() + "******************* constructRules");
		logger.debug(config.getList("threshold.id").size());
		for (int i = 0; i < config.getList("threshold.id").size(); i++) {
			sub = config.configurationAt("threshold(" + i + ")");
			rules.add(convertElementToRule(sub));
		}
		return rules;
	}
	
	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 * @throws RuleDataIDNotExistException
	 * @throws ConfigurationException
	 * @throws RuleDataIDExistException
	 */
	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, ClassNotFoundException, ParseException,
			ConfigurationException, RuleDataIDNotExistException,
			RuleDataIDExistException {
		// TODO Auto-generated method stub
		CustomerInfo info = new CustomerInfo();
		info.setCustomerName("test");
		RuleFactory factory = RuleFactory.getInstance();
		List<Rule> list = new ArrayList<Rule>();
		Rule r;
		RuleData data;
		/*
		 * for (int i = 0; i < 10; i++){ data = new RuleData(); data.setId(i); r
		 * = new Rule(data); list.add(r); }
		 * 
		 * for(Rule rule : list){ System.out.println(rule.getData().getId()); }
		 * boolean removeFlag = false; int removeIndex = 0; for(Rule rule :
		 * list){ if(rule.getData().getId() == 5){ removeFlag = true; }
		 * if(!removeFlag) removeIndex++; } System.out.println(removeIndex +
		 * "***************"); if(removeFlag) list.remove(removeIndex);
		 * 
		 * for(Rule rule : list){ System.out.println(rule.getData().getId()); }
		 */

		data = new RuleData();
		data.setId(5);
		Rule rule = new Rule(data);
		data.setId(8);
		// Rule rule = factory.getRules().get(2);
		// data = rule.getData();
		data.setDescription("description 惨痛教训");
		data.setCondition("test");
		// data.setValue(20);
		data.setType("type");
		data.setAttribute("test");
		List<String> params = new ArrayList<String>();
		params.add("test1");
		params.add("test2");
		params.add("test3");
		// data.setParams(params);
		factory.addRule(rule);
		for (Rule l : factory.getRules()) {
			System.out.println(l.getData().getId());
		}
		System.out.println(info.getRiskValue());
		System.out.println(info.getRiskType());
	}

}

package com.elulian.CustomerSecurityManagementSystem.service;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

public class Rule {

	private static Logger logger = Logger.getLogger(Rule.class);

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	// All the attributes in CustomerInfo
	private static Field[] fields = CustomerInfo.class.getDeclaredFields();

	// Currently support attribute types for rules, means support types for
	// CustomerInfo fields
	@SuppressWarnings({ "unused", "unchecked" })
	private static final Class[] supportTypes = { Long.class, Integer.class, Short.class, Float.class, Double.class, Date.class,
			String.class, Boolean.class };

	private RuleData data;

	public Rule(RuleData data) {
		this.data = data;
	}

	/**
	 * Execute the rule: find out the field, and check it with rule data. If
	 * match, set risk value and type for CustomerInfo
	 * 
	 * @param info
	 *            CustomerInfo need to be checked
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 *             when no such data Type for CustomerInfo attributes
	 * @throws ParseException 
	 */
	public void execute(CustomerInfo info) throws IllegalArgumentException,
			IllegalAccessException, ClassNotFoundException, ParseException {
		// list all fieldss of CustomerInfo, find out the field need to check
		for (int i = 0, len = fields.length; i < len; i++) {
			// get field name
			String varName = fields[i].getName();
			// if it is the exact field, change accessibility and check risk
			// (value and type)
			if (varName.equals(data.getAttribute())) {
				fields[i].setAccessible(true);
				logger.debug(logger.getName() + "******************** execute");
				logger.debug(fields[i].get(info));
				setRiskForAttribute(info, fields[i].get(info),fields[i].getType());
				break;
			}
		}
	}

	/**
	 * 
	 * @param info
	 * @param attri
	 * @param type 
	 * @throws ClassNotFoundException
	 * @throws ParseException 
	 */
	private void setRiskForAttribute(CustomerInfo info, Object attri, Class<?> type)
			throws ClassNotFoundException, ParseException {
		assert Class.forName(data.getType()).equals(type) : "Attribute type in rule data should match field type";
		boolean match = false;
		if(type.isInstance(Long.class))
			match = checkRiskForLongAttri(((attri == null) ? null : (Long)attri));
		if(type.isInstance(Integer.class))
			match = checkRiskForIntegerAttri(((attri == null) ? null : (Integer)attri));
		if(type.isInstance(Short.class))
			match = checkRiskForShortAttri(((attri == null) ? null : (Short)attri));
		if(type.isInstance(Float.class))
			match = checkRiskForFloatAttri(((attri == null) ? null : (Float)attri));
		if(type.isInstance(Double.class))
			match = checkRiskForDoubleAttri(((attri == null) ? null : (Double)attri));
		if(type.isInstance(Byte.class))
			match = checkRiskForByteAttri(((attri == null) ? null : (Byte)attri));
		else if(type.equals(Boolean.class))
			match = checkRiskForBooleanAttri((attri == null) ? null : (Boolean)attri);
		else if (type.equals(String.class))
			match = checkRiskForStringAttri((attri == null) ? null : (String)attri);
		else if (type.equals(Date.class))
			match = checkRiskForDateAttri((attri == null) ? null : (Date)attri);
		else
			logger.error("Unsupport data type");
		if(match)
			setRiskForCustomerInfo(info, data.getValue(), data.getDescription());		
	}

	
/*	public <T extends Comparable<?>> boolean test(T attri, Class<?> type){
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if (attri.compareTo(parseNumber(param, )) {
					return true;
				}
			}
			return false;
		}
		return false;
	}*/
	
	private boolean checkRiskForIntegerAttri(Integer attri) {
		if ("eq".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Integer.parseInt(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if ("ne".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Integer.parseInt(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if ("lt".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Integer.parseInt(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if ("gt".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Integer.parseInt(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if ("between".equals(data.getCondition())) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Integer.parseInt(param1)
						&& attri <= Integer.parseInt(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}
	
	private boolean checkRiskForShortAttri(Short attri) {
		if ("eq".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Short.parseShort(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if ("ne".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Short.parseShort(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if ("lt".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Short.parseShort(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if ("gt".equals(data.getCondition())) {
			for (String param : data.getParams()) {
				if (Short.parseShort(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if ("between".equals(data.getCondition())) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Short.parseShort(param1)
						&& attri <= Short.parseShort(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}
	
	private boolean checkRiskForFloatAttri(Float attri) {
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if (Float.parseFloat(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if (Float.parseFloat(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("lt")) {
			for (String param : data.getParams()) {
				if (Float.parseFloat(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("gt")) {
			for (String param : data.getParams()) {
				if (Float.parseFloat(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if (data.getCondition().equals("between")) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Float.parseFloat(param1)
						&& attri <= Float.parseFloat(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}
	
	private boolean checkRiskForDoubleAttri(Double attri) {
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if (Double.parseDouble(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if (Double.parseDouble(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("lt")) {
			for (String param : data.getParams()) {
				if (Double.parseDouble(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("gt")) {
			for (String param : data.getParams()) {
				if (Double.parseDouble(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if (data.getCondition().equals("between")) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Double.parseDouble(param1)
						&& attri <= Double.parseDouble(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}
	
	private boolean checkRiskForByteAttri(Byte attri) {
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if (Byte.parseByte(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if (Byte.parseByte(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("lt")) {
			for (String param : data.getParams()) {
				if (Byte.parseByte(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("gt")) {
			for (String param : data.getParams()) {
				if (Byte.parseByte(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if (data.getCondition().equals("between")) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Byte.parseByte(param1)
						&& attri <= Byte.parseByte(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}

	private boolean checkRiskForLongAttri(Long attri) {
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if (Long.parseLong(param) == attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if (Long.parseLong(param) != attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("lt")) {
			for (String param : data.getParams()) {
				if (Long.parseLong(param) >= attri) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("gt")) {
			for (String param : data.getParams()) {
				if (Long.parseLong(param) <= attri) {
					return true;
				}
			}
			return false;
		}
		
		if (data.getCondition().equals("between")) {
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (attri >= Long.parseLong(param1)
						&& attri <= Long.parseLong(param2)) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}
	
	/**
	 * Check equal/not equal for boolean attribute (TRUE/FALSE) 
	 * Need to add throws expection later
	 * 
	 * @param attri
	 *            the attribute to check
	 * @return If attri is null or match the rule, return true. Else return false.
	 */
	private boolean checkRiskForBooleanAttri(Boolean attri) {
		if(attri == null)
			return true;
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				logger.debug(logger.getName() + "******************** checkRiskForBooleanAttri");
				logger.debug(param);
				if (attri == Boolean.parseBoolean(param)) {
					return true;
				}
			}
			return false;
		}
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if (attri != Boolean.parseBoolean(param)) {
					return true;
				}
			}
			return false;
		}
		logger.debug("Unsupport condition for Boolean");
		return false;
	}

	/**
	 * Check equal/not equal/contains condition for string attribute
	 * 
	 * @param attri
	 *            the attribute to check
	 * @return return true if match rule, else return false
	 */
	private boolean checkRiskForStringAttri(String attri) {
		// equal: special string (null) or other strings
		if (data.getCondition().equals("eq")) {
			if (attri == null)
				return true;
			for (String param : data.getParams()) {
				if (attri.equalsIgnoreCase(param)) {
					return true;
				}
			}
			return false;
		}
		// not equal: special string (null) or other strings
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if ((param.equalsIgnoreCase("null") && attri != null)
						|| (attri != null && !attri.equalsIgnoreCase(param))) {
					return true;
				}
			}
			return false;
		}
		// contains
		if (data.getCondition().equals("contains")) {
			for (String param : data.getParams()) {
				if (attri != null && attri.contains(param)) {
					return true;
				}
			}
			return false;
		}

		logger.error("Unsupport condition type");
		return false;
	}

	/**
	 * Check equal/not equal/before/after/contains condition for Date attribute
	 * Date format pattern(Hard code): see dateFormat (yyyy-MM-dd)
	 * Special string: null, today
	 * @param attri
	 *            the attribute to check
	 * @return return true if match rule, else return false
	 * @throws ParseException If date parameter in RuleDate doesn't meet the format
	 */
	private boolean checkRiskForDateAttri(Date attri) throws ParseException {
		Date date = null;
		if(attri != null)
			date = dateFormat.parse(dateFormat.format(attri));
		// equal special string (null, today) or other strings with format
		if (data.getCondition().equals("eq")) {
			for (String param : data.getParams()) {
				if ((param.equalsIgnoreCase("null") && attri == null) || param.equalsIgnoreCase("today") && attri != null && date.equals(dateFormat.parse(dateFormat.format(new Date())))
						|| (attri != null && (date.equals(dateFormat.parse(param))))) {
					return true;
				}
			}
			return false;
		}
		// not equal special string (null) or other strings
		if (data.getCondition().equals("ne")) {
			for (String param : data.getParams()) {
				if ((param.equalsIgnoreCase("null") && attri != null) || param.equalsIgnoreCase("today") && attri != null && !date.equals(dateFormat.parse(dateFormat.format(new Date())))
						|| (attri != null &&!(date.equals(dateFormat.parse(param))))) {
					return true;
				}
			}
			return false;
		}
		// before special string (today) or other strings with format
		if (data.getCondition().equals("lt")) {
			if ( attri == null)
				return false;
			for (String param : data.getParams()) {
				if (param.equalsIgnoreCase("today") && date.compareTo(dateFormat.parse(dateFormat.format(new Date()))) <= 0
						|| date.compareTo(dateFormat.parse(param)) <= 0 ) {
					return true;
				}
			}
			return false;
		}
		// after special string (today) or other strings with format
		if (data.getCondition().equals("gt")) {
			if ( attri == null)
				return false;
			for (String param : data.getParams()) {
				if (param.equalsIgnoreCase("today") && date.compareTo(dateFormat.parse(dateFormat.format(new Date()))) >= 0
						|| date.compareTo(dateFormat.parse(param)) >= 0) {
					return true;
				}
			}
			return false;
		}

		// between
		if (data.getCondition().equals("between")) {
			if ( attri == null)
				return false;
			String param1 = data.getParams().get(0);
			String param2 = data.getParams().get(1);
				if (date.compareTo(dateFormat.parse(param1)) >= 0
						&& date.compareTo(dateFormat.parse(param2)) <= 0) {
					return true;
				}
			return false;
		}
		logger.error("Unsupport condition type");
		return false;
	}

	/**
	 * set risk value and type for CustomerInfo
	 * @param info 
	 * 			  CustomerInfo need to be set
	 * @param riskValue
	 *            risk value
	 * @param riskType
	 *            risk type
	 */
	private void setRiskForCustomerInfo(CustomerInfo info, Integer riskValue, String riskType) {
		addRiskValueForCustomerInfo(info, riskValue);
		addRiskTypeForCustomerInfo(info, riskType);
	}

	/**
	 * set risk value for CustomerInfo
	 * @param info 
	 * 
	 * @param riskValue
	 */
	private void addRiskValueForCustomerInfo(CustomerInfo info, Integer riskValue) {
		// CustomerInfo's riskValue should not be null
		if (info.getRiskValue() == null)
			info.setRiskValue(0);
		info.setRiskValue(info.getRiskValue() + riskValue);
	}

	/**
	 * set risk type for CustomerInfo
	 * @param info 
	 * 
	 * @param riskType
	 */
	private void addRiskTypeForCustomerInfo(CustomerInfo info, String riskType) {
		// The first time to write risk type in info
		if (info.getRiskType() == null || info.getRiskType().isEmpty())
			info.setRiskType(riskType);
		else
			// Append type to info, already contains other types
			info.setRiskType(info.getRiskType() + "; " + riskType);
	}

	/**
	 * Compare two dates, just compare year, month, day, no hour, minute, second is included.
	 * 
	 * @param date1 date to compare
	 * @param date2 date to compare
	 * @return If result > 0, means date1 is before date2,
	 * 		   If result = 0, means date1 is equal date2,
	 * 		   If result < 0, means date1 is after date2.
	 */
	@Deprecated
	private long compareDate(Date date1, Date date2){
		assert date1 != null : "Date should not be null";
		assert date2 != null : "Date should not be null";
		
		return 0;
	}

	
	public RuleData getData() {
		return data;
	}

	public void setData(RuleData data) {
		this.data = data;
	}
	
	 /**
     * Creates a copy of this object. This implementation will create a deep
     * clone, i.e. the RuleData that stores the data is cloned, too. So changes
     * performed at the copy won't affect the original and vice versa.
     *
     * @return the copy
     * @depecated how to copy ruleData is not available
     */
	@Deprecated
    public Object clone()
    {
        try
        {
            Rule copy = (Rule) super.clone();
            copy.data = (RuleData)this.data.clone();
            return copy;
        }
        catch (CloneNotSupportedException cex)
        {
            logger.error(logger.getName() + "*******" + "Clone rule failed");
        	// should not happen
            throw new RuntimeException(cex);
        }
    }

}

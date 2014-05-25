package com.elulian.CustomerSecurityManagementSystem.web.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private static Logger logger = Logger.getLogger(DateConverter.class);

	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Date date = null;
		String dateString = null;
		if (values != null && values.length > 0) {
			dateString = values[0];
			if (dateString != null) {
				
				try {
					date = format.parse(dateString);
				} catch (ParseException e) {
					date = null;
				}
			}
		}
		logger.debug(logger.getName() + "****************convertFromString");
		logger.debug(date);
		return date;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map context, Object o) {
		// 格式化为date格式的字符串
        return format.format((Date)o);   
	}

}

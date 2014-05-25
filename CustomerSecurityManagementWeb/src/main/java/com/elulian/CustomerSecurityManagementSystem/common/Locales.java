package com.elulian.CustomerSecurityManagementSystem.common;

import static com.elulian.CustomerSecurityManagementSystem.common.ConfigServlet.getLocaleProps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class Locales {

	private static Logger logger = Logger.getLogger(Locales.class);

	private static Map<String, Locale> locales = new HashMap<String, Locale>();

	private static PropertiesConfiguration localeProps;
	
	private Locale current; 

	public void setCurrent(Locale current){ 
		this.current = current; 
	} 
	
	@SuppressWarnings("unchecked")
	public Map<String, Locale> getLocales() {
		locales.clear();
		// get resource from globalMessages
		ResourceBundle bundle = ResourceBundle.getBundle("globalMessages" , current); 
		localeProps = getLocaleProps();
		if(!localeProps.isEmpty()){	
			Iterator<String> i = localeProps.getKeys();
			String key;
			String[] langFlags;
			while (i.hasNext()) {
				key = i.next();
				langFlags = localeProps.getString(key).split("_");
				if(langFlags.length == 2)
					locales.put(bundle.getString(key), new Locale(langFlags[0],langFlags[1]));
				else
					locales.put(bundle.getString(key), new Locale(langFlags[0]));
				logger.debug("get property with key: " + key);
			}
		} else {
			locales.put("English", Locale.US);
			locales.put("Chinese", Locale.CHINA);
		}			
		return locales;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Locales locales = new Locales();
		locales.setCurrent(Locale.CHINA);
		
			/*for (String locale : locales.getLocales().keySet()) {
				System.out.println(locale);
			}*/
		/*for (Locale locale : locales.getLocales().values()) {
			System.out.println(locale.getDisplayName());
			System.out.println(locale.equals(Locale.CHINA));
			System.out.println(locale.equals(Locale.US));
//			System.out.println(locale == Locale.CHINA);
		}*/
		/*Locale l = new Locale("zh", "CN");
		
		System.out.println(l.getDisplayName());
		System.out.println(l.equals(Locale.CHINA));
		System.out.println(l == Locale.CHINA);*/
		System.out.println(Locale.getDefault().toString());
	}
}

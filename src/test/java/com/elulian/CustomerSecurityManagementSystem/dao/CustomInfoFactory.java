package com.elulian.CustomerSecurityManagementSystem.dao;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;


public class CustomInfoFactory {
    
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	public static Collection getBeanCollection()   
    {   
		Condition condition = new Condition();
		condition.setStartRow(0);
		condition.setMaxRow(5000);
//		condition.setSearchBranch(searchBranch);
        return ServiceFactory.getServiceFactory().getICustomerInfoService().getCustomerInfosByCondition(condition);   
    } 
	
	public static boolean check(int num) {
		if (num <= 0)
			return false;
		
		if( num % 7 == 0)
			return true;
		if(num % 10 == 7)
			return true;
		
		return false;
	}
	
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException{
		
		/*	try {
				Date date1 = new SimpleDateFormat("yyyyMMdd").parse("20090306");
				Date date2 = new SimpleDateFormat("yyyyMMddhhmmss").parse("20090306162030");
				System.out.println(date1);
				System.out.println(date2);
				System.out.println(dateFormat.parse(dateFormat.format(date2)).equals(date1));
				System.out.println(dateFormat.parse(dateFormat.format(date2)).compareTo(date1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		/*System.out.println(check(17));
		System.out.println(check(-5));
		System.out.println(check(170));
		System.out.println(check(77));
		System.out.println(check(28));
		System.out.println(check(707));
		System.out.println(check(490000));*/
//		System.out.println(new String("总部".getBytes("UTF-8")));
		File file = new File("searchTickets.pl");
//		System.out.println(file.exists());
		if(file.exists())
			file.delete();
		System.out.println(file.length());
		
	}
}

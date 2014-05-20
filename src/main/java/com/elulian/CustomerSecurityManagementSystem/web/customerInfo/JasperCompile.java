package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

import java.io.File;

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperCompile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 /*
	     * Here we compile our xml jasper template to a jasper file.
	     * Note: this isn't exactly considered 'good practice'.
	     * You should either use precompiled jasper files (.jasper) or provide some kind of check
	     * to make sure you're not compiling the file on every request.
	     * If you don't have to compile the report, you just setup your data source (eg. a List)
	     */
	    try {
	      String reportSource = JasperCompile.class.getResource("/customerInfo/jasper_template.jrxml").getPath();
	      File parent = new File(reportSource).getParentFile();
	      JasperCompileManager.compileReportToFile(reportSource,
	        new File(parent, "compiled_jasper_template.jasper").getAbsolutePath());
	    }catch (Exception e) {
	     e.printStackTrace();
	      
	    }
	}

}

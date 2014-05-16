package com.elulian.CustomerSecurityManagementSystem.dao.utility;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.jdbc.meta.MappingTool;

@Deprecated // replaced by maven
public class OpenJPAUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		//For enahnc all java files to classes files. 
		  // Please get the enhanced classes in classes folder
		 generateEnhancers();
		
		// For mapping classes and tables
		try {
			mappingClasses();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void generateEnhancers() {
		System.out.println(OpenJPAUtility.class.getResource("./"));
		String[] enhancers = new String[1];
		for (int i = 0; i <= 3; i++) {
			if (i == 0)
				enhancers[0] = System.getProperty("user.dir") + "./src/vo/UserInfo.java";
			if (i == 1)
				enhancers[0] = System.getProperty("user.dir") + "./src/vo\\CustomerInfo.java";
			if (i == 2)
				enhancers[0] = System.getProperty("user.dir") + "./src/vo\\RiskRank.java";
			if (i == 3)
				enhancers[0] = System.getProperty("user.dir") + "./src/vo\\Threshold.java";
//			enhancers[1] = "-d";
//			enhancers[2] = "../../enhancedClasses";
			// enhancers[4] = "UserInfo.java";
			PCEnhancer.main(enhancers);
		}
	}

	public static void mappingClasses() throws IOException, SQLException {
		// new MappingTools();
//		System.out.println(System.getProperty("user.dir"));
		String[] enhancers = new String[4];
		enhancers[0] = System.getProperty("user.dir") + "./src/vo\\CustomerInfo.java";
		enhancers[1] = System.getProperty("user.dir") + "./src/vo\\UserInfo.java";
		enhancers[2] = System.getProperty("user.dir") + "./src/vo\\RiskRank.java";
		enhancers[3] = System.getProperty("user.dir") + "./src/vo\\Threshold.java";
		MappingTool.main(enhancers);
	}

}

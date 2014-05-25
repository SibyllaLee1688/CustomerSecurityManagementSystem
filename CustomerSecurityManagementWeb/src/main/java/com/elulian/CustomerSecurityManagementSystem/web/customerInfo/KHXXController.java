package com.elulian.CustomerSecurityManagementSystem.web.customerInfo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.elulian.CustomerSecurityManagementSystem.service.IThresholdService;
import com.elulian.CustomerSecurityManagementSystem.service.ServiceFactory;
import com.elulian.CustomerSecurityManagementSystem.vo.CustomerInfo;

public class KHXXController {

	private static Logger logger = Logger.getLogger(KHXXController.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");

	        EntityManager em = factory.createEntityManager();

	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        long startTime = System.currentTimeMillis();
	        Query q = em.createQuery("select count(t) from CustomerInfo t");
	        long count = (Long) q.getResultList().get(0);
	        q = null;
	        List<CustomerInfo> list = null;
	        String temp = null;
	        // should replace with a list of thresholds with execute method
	        IThresholdService service = ServiceFactory.getServiceFactory().getIThresholdService();
	        for(int i = 0; i <= count; i = i + 50) {
	        	q = em.createQuery("select t from CustomerInfo t");
	        	q.setFirstResult(i);
	        	q.setMaxResults(50);
	        	list = q.getResultList();
	        	for(CustomerInfo info : list){
	        		service.setMatchThresholdsInfo(info);
//	        		not required, since EM is not closed.
//	        		em.merge(info);
	        	}
	        	em.flush();
       		 	em.clear();
	        }
	        long endTime = System.currentTimeMillis();
	        logger.debug("Disposal time: " + (endTime - startTime));
	        tx.commit();
	        em.close();
	        factory.close();
	    }    
}

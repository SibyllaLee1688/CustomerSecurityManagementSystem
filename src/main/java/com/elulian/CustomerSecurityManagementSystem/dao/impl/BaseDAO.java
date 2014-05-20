package com.elulian.CustomerSecurityManagementSystem.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.elulian.CustomerSecurityManagementSystem.dao.IBaseDAO;

public  class BaseDAO<T, ID extends Serializable> implements IBaseDAO<T, ID> {

	private final Logger logger = Logger.getLogger(getClass());
	
	private Class<T> persistentClass;

	@PersistenceContext  
	@Autowired
	protected EntityManager entityManager;  
	
	@SuppressWarnings("unchecked")
	public BaseDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/*@Override
	public void delete(T entity) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();
		
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(entityManager.merge(entity));
		
		 tx.commit();
		em.close();
		factory.close();
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(){
	/*	EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery("select t from "
				+ persistentClass.getSimpleName() + " t");
		List<T> list = q.getResultList();
		/*em.close();
		factory.close();*/
		return list;
	}

	@Override
	public T findById(ID id) {
		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		T t = entityManager.find(persistentClass, id);
	/*	em.close();
		factory.close();*/
		return t;
	}

	@Deprecated
	public List<T> getOnePage(int startRow, int pageSize){
		String hql = "select t from " + persistentClass.getName() + " t";
		return getOnePage(hql, startRow, pageSize);
	}

	/**
	 * Get a list of T with start row and limit numbers.
	 * 
	 * @param hql The hql to execute.
	 * @param startRow should >= 0, If startRow < 0, set to 0.
	 * @param pageSize should > 0, If paseSize <= 0, ignore this param.
	 * @return The selected T object list
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getOnePage(String hql, int startRow, int pageSize){
//		assert startRow >= 0 : "startRow should >= 0";
//		assert pageSize > 0 : "pageSize should > 0";
		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery(hql);
		if (startRow < 0)
			startRow = 0;
		q.setFirstResult(startRow);
		if (pageSize > 0)
			q.setMaxResults(pageSize);
		/*em.close();
		factory.close();*/
		return q.getResultList();
	}

	@Override
	public long getTotalCount(){
		Long count = new Long(0);

		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery("select count(t) from "
				+ persistentClass.getSimpleName() + " t");
		if (!q.getResultList().isEmpty()) {
			count = (Long) q.getResultList().get(0);
		}
		/*em.close();
		factory.close();*/
		return count;
	}

	
	/**
	 * Count the number of T entities with hql
	 * 
	 * @param hql The hql to execute
	 * @param classname object name used in hql
	 * @return the number, 0 if no entity found
	 */
	protected long getTotalCount(String hql, String classname) {
		assert hql.contains(classname) : "object name should be contains in hql";
		Long count = new Long(0);
//		remove orderby in hql;
		int fromIndex = hql.indexOf("from");
		int orderByIndex = hql.indexOf("order by");

		StringBuffer buff = new StringBuffer();
		if(fromIndex == -1){
			// Illegal hql
			return 0;
		}else if(orderByIndex > 0){
			// remove orderby
			buff.append("select count(");
			buff.append(classname);
			buff.append(") ");
			buff.append(hql.substring(fromIndex, orderByIndex));
		}else{
			buff.append("select count(");
			buff.append(classname);
			buff.append(") ");
			buff.append(hql.substring(fromIndex));
		}
		logger.debug(buff.toString());
		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery(buff.toString());
		if (!q.getResultList().isEmpty()) {
			count = (Long) q.getResultList().get(0);
		}
		/*em.close();
		factory.close();*/
		return count.longValue();
	}

	@Override
	public boolean exists(ID id) {
		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery("select count(t) from "
				+ persistentClass.getSimpleName() + " t");
		/*em.close();
		factory.close();*/
		return q.getResultList().isEmpty();
	}

	@Override
	//@Transactional
	public T save(T object) {
		//T o =
		return entityManager.merge(object);
		/* flush is not required, will be automatically called by transaction commit */
		/* if you need to catch yourself exception in service layer instead of web layer, you need to 
		 * use flush here to make sure the transaction happens during service function call not 
		 * after service function call		 * 
		 */
		//entityManager.flush();	
		//return o;
	}

	@Override
	//@Transactional done in service layer
	public void remove(T object) {		
		entityManager.remove(object);
	}

	@Override
	//@Transactional
	public void remove(ID id) {
		entityManager.remove(findById(id));
	}

	@Override
	public List<T> search(String searchTerm) {
		// TODO Auto-generated method stub
		return null;
	}

}

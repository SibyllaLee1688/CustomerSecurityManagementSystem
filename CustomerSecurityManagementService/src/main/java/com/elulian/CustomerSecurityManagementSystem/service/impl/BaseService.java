package com.elulian.CustomerSecurityManagementSystem.service.impl;

import com.elulian.CustomerSecurityManagementSystem.dao.IBaseDAO;
import com.elulian.CustomerSecurityManagementSystem.exception.DataMissingException;
import com.elulian.CustomerSecurityManagementSystem.exception.ExistsException;
import com.elulian.CustomerSecurityManagementSystem.service.IBaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

import javax.annotation.security.RolesAllowed;


/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * 
 *
 * @param <T>  a type variable
 * @param <ID> the primary key for that type
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Updated by jgarcia: added full text search + reindexing
 */
@RolesAllowed({"ROLE_ADMIN"})
public class BaseService<T, ID extends Serializable> implements IBaseService<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * GenericDao instance, set by constructor of child classes
     */
    protected IBaseDAO<T, ID> dao;


    public BaseService() {
    }

    public BaseService(IBaseDAO<T, ID> baseDAO) {
        this.dao = baseDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public List<T> findAll() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public T findById(ID id) {
        return dao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public boolean exists(ID id) {
        return dao.exists(id);
    }

    /**
     * {@inheritDoc}
     * @throws ExistsException 
     * @throws DataMissingException 
     */
    @Override
	public T save(T object) { // throws ExistsException, DataMissingException {
    	
    	//T o = null;
    	
    	/*
		 * The implementation depends on when the transaction happens.
		 * 
		 * If save transaction is controlled by spring transactional in service layer,
		 * 
		 * the catch statement doesn't work due to the transaction happens at the end of
		 * 
		 * this method, spring data integrity exception or invalid exception throws in spring
		 * 
		 * junit test. If save transaction is controlled in dao layer (call flush function inside
		 * 
		 * save function), entirytexists/invalidstate can be sucessfully catched and exists/datamissing
		 * 
		 * exception can be caught in junit test.
		 * 
		 * Follow spring transaction control rules here to make sure signal center transaction
		 * 
		 * control logic
		 *  
		 */
    	
    	
    	return dao.save(object);
    	
        /*try{
        	o = dao.save(object);
        	
        } catch (EntityExistsException e) {
        	throw new ExistsException(e);
        } catch (InvalidStateException e){
        	throw new DataMissingException(e);
        } 
        
        return o;*/
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void remove(T object) {
        	dao.remove(object);       
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void remove(ID id) {
        	dao.remove(id);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Search implementation using Hibernate Search.
     */
    @Override
	public List<T> search(String q) {
        if (null == q || "".equals(q.trim())) {
            return findAll();
        }
        
        return dao.search(q);
    }

	@Override
	public long getTotalCount() {
		return dao.getTotalCount();
	}

	@Override
	public void setDAO(IBaseDAO<T, ID> dao) {
		this.dao = dao;
	}
    
    

}

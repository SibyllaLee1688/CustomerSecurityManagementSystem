package com.elulian.CustomerSecurityManagementSystem.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elulian.CustomerSecurityManagementSystem.dao.IUserInfoDAO;
import com.elulian.CustomerSecurityManagementSystem.vo.Condition;
import com.elulian.CustomerSecurityManagementSystem.vo.UserInfo;

@Repository("userInfoDAO")
public class UserInfoDAO extends BaseDAO<UserInfo, Integer> implements
		IUserInfoDAO, UserDetailsService {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserInfo user = this.getUserByName(username);
		if(null == user){
			logger.warn("fail to use " + username + "to load User++++++++++++++++++++++++, result is null");
			throw new UsernameNotFoundException(username);
		}
		return user;
	}	
	
	@Override
	public UserInfo getUserByName(String name) {
		/*EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		// �� EntityManagerFactory ʵ�� factory �л�ȡ EntityManager
		EntityManager em = factory.createEntityManager();*/
		Query q = entityManager.createQuery("select t from UserInfo t where t.username = '"
				+ name + "'");
		@SuppressWarnings("unchecked")
		List<UserInfo> list = q.getResultList();
		/*em.close();
		factory.close();*/
		if(list.isEmpty())
			return null;
		return list.get(0);
	}

	
	/*@Override
	public UserInfo getUserInfoByUserId(String userId) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("mysql");
		// �� EntityManagerFactory ʵ�� factory �л�ȡ EntityManager
		EntityManager em = factory.createEntityManager();
		Query q = entityManager.createQuery("select t from UserInfo t where t.userId = '"
				+ userId + "'");
		q.setHint("openjpa.hint.OptimizeResultCount", new Integer(1));
		@SuppressWarnings("unchecked")
		List<UserInfo> list = q.getResultList();
		em.close();
		factory.close();
		if(list.isEmpty())
			return null;
		return list.get(0);
	}
*/
	@Override
	public long getTotalCount(Condition condition) {
		StringBuffer hql = new StringBuffer(
				"select userInfo from UserInfo userInfo where ");
		boolean flag = false;
		if (condition.getSearchBranch() != null
				&& condition.getSearchBranch().length() != 0
				&& (!condition.getSearchBranch().equalsIgnoreCase("ALL"))) {
			flag = true;
			hql.append("userInfo.branch like '%" + condition.getSearchBranch()
					+ "%' ");
		}
		if (condition.getUsername() != null
				&& condition.getUsername().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.username = '" + condition.getUsername() + "' ");
		}
		if (null != condition.getRealname()
				&& 0 != condition.getRealname().length()) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.realname = '" + condition.getRealname() + "' ");
		}
		if (!flag) {
			hql = new StringBuffer(" select userInfo from UserInfo userInfo ");
		}
		return super.getTotalCount(hql.toString(), "userInfo");
	}

	/**
	 * @param condition should not be null
	 * @return Result list (UserInfo), list is empty if no result found
	 */
	@Override
	public List<UserInfo> getUserInfoByCondition(Condition condition) {
		boolean flag = false;
		StringBuffer hql = new StringBuffer(
				"select userInfo from UserInfo userInfo where ");
		if (condition.getSearchBranch() != null
				&& condition.getSearchBranch().length() != 0
				&& (!condition.getSearchBranch().equalsIgnoreCase("ALL"))) {
			flag = true;
			hql.append("userInfo.branch like '%" + condition.getSearchBranch()
					+ "%' ");
		}
		if (condition.getUsername() != null
				&& condition.getUsername().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.username = '" + condition.getUsername() + "' ");
		}
		if (condition.getRealname() != null
				&& condition.getRealname().length() != 0) {
			if (flag)
				hql.append(" and ");
			flag = true;
			hql.append("userInfo.realname = '" + condition.getRealname() + "' ");
		}
		if (!flag) {
			hql = new StringBuffer(
					" select userInfo from UserInfo userInfo order by userInfo.branch desc");
		} else {
			hql.append(" order by userInfo.branch desc");
		}
		logger.debug("UserInfoDAO user condition to hql----> " + hql);
		return super.getOnePage(hql.toString(), condition.getStartRow(),
				condition.getMaxRow());
	}

	/*public static void main(String[] args) {

		UserInfoDAO dao = new UserInfoDAO();
		UserInfo user = null;

		// test save

		
		 * for(int i = 2; i < 20; i++){ user = new UserInfo();
		 * user.setName("test" + i); user.setPassword("test" + i);
		 * user.setBranch("test" + i); dao.saveOrUpdate(user); }
		 

		
		 * // test list for (UserInfo u : dao.findAll()) {
		 * System.out.println(u.getName()); System.out.println(u.getId());
		 * System.out.println(u.getPassword());
		 * System.out.println(u.getBranch()); }
		 

		
		 * //test update user = dao.findById(new Integer(4)); //
		 * user.setName("test1"); user.setPassword("test2");
		 * user.setBranch("test2"); dao.saveOrUpdate(user);
		 * 
		 * user = dao.findById(new Integer(4));
		 * System.out.println(user.getName()); System.out.println(user.getId());
		 * System.out.println(user.getPassword());
		 * System.out.println(user.getBranch());
		 

		
		 * //test delete user = dao.findById(new Integer(2));
		 * System.out.println(user.getName()); System.out.println(user.getId());
		 * System.out.println(user.getPassword());
		 * System.out.println(user.getBranch()); dao.delete(user);
		 
		
		 * //test total count
		 * 
		 * System.out.println(dao.getTotalCount());
		 
		
		 * //test get user by name
		 * 
		 * user = dao.getUserByName("test10");
		 * System.out.println(user.getName()); System.out.println(user.getId());
		 * System.out.println(user.getPassword());
		 * System.out.println(user.getBranch());
		 
		// test Condition
		Condition condition = new Condition();
		condition.setSearchBranch("1");
		condition.setUsername("");
		condition.setStartRow(-5);
		condition.setMaxRow(0);
		System.out.println(dao.getTotalCount(condition));
		for (UserInfo u : dao.getUserInfoByCondition(condition)) {
			System.out.println(u.getName());
		}
	
		user = dao.getUserInfoByUserId("admin");
		System.out.println(user.getName()); 
		System.out.println(user.getUserId());
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getBranch());
//		System.out.println(dao.findById(new Integer(400)));
	}*/
}

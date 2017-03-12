package com.upm.etsist.security.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.security.model.User;


public class UserDAOImpl implements UserDAO {
 
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public User findById(int id) {
    	Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from User where id = :id");
		query.setParameter("id", id);
		List<User> list = query.list();
		session.close();
		return list.get(0);
    }
 
    public User findBySSO(String sso) {
    	Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from User where sso_id = :sso");
		query.setParameter("sso", sso);
		List<User> list = query.list();
		session.close();
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
    }
 
	@Override
	public void save(User user) throws ConstraintViolationException {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.save(user);
			session.flush();
			session.clear();
			tx.commit();
		}catch(ConstraintViolationException e){
			throw e;
		}finally{
			session.close();
		}
	}

	@Override
	public void update(User user) throws ConstraintViolationException {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.update(user);
			session.flush();
			session.clear();
			tx.commit();
		}catch(ConstraintViolationException e){
			throw e;
		}finally{
			session.close();
		}
	}

	@Override
	public void delete(User user) throws ConstraintViolationException {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.delete(user);
			session.flush();
			session.clear();
			tx.commit();
		}catch(ConstraintViolationException e){
			throw e;
		}finally{
			session.close();
		}
	}

	@Override
	public void deleteAll() throws ConstraintViolationException {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from User").executeUpdate();
		tx.commit();
		session.close();
	}
	
	@Override
	public List<User> findAll() throws ConstraintViolationException {
		Session session = this.sessionFactory.openSession();
		List<User> userList = session.createQuery("from User").list();
		session.close();
		return userList;
	}

}
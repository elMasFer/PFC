package com.upm.etsist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.upm.etsist.dto.SearchResponseDTO;

public class SearchResponseDAOImpl implements SearchResponseDAO{
	
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	@Override
	public void save(SearchResponseDTO response) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.save(response);
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
	public void saveAll(List<SearchResponseDTO> responsesList) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		for(int i=0;i<responsesList.size();i++){
			SearchResponseDTO response=responsesList.get(i);
			try{
				session.save(response);
				session.flush();
				session.clear();
				tx.commit();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				session.close();
				session = this.sessionFactory.openSession();
				tx = session.beginTransaction();
			}
		}
		
		session.close();
	}

	@Override
	public void update(SearchResponseDTO response) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.update(response);
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
	public void delete(SearchResponseDTO response) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try{
			session.delete(response);
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
	public void deleteAll() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.createQuery("delete from SearchResponseDTO").executeUpdate();
		tx.commit();
		session.close();
	}

	@Override
	public List<SearchResponseDTO> findSPA(String text) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from SearchResponseDTO where lower(spanishText) like lower(:text) order by validated desc, length(spanishText)");
		query.setParameter("text", "%"+text+"%");
		List<SearchResponseDTO> list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<SearchResponseDTO> findENG(String text) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from SearchResponseDTO where lower(englishText) like lower(:text) order by validated desc, length(englishText)");
		query.setParameter("text", "%"+text+"%");
		List<SearchResponseDTO> list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<SearchResponseDTO> findAll() {
		Session session = this.sessionFactory.openSession();
		List<SearchResponseDTO> searchResponseDTOList = session.createQuery("from SearchResponseDTO").list();
		session.close();
		return searchResponseDTOList;
	}

}

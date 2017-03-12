package com.upm.etsist.security.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;

import com.upm.etsist.security.model.User;

public interface UserDAO {
	 
    User findById(int id);
     
    User findBySSO(String sso);

	void save(User user) throws ConstraintViolationException;

	void update(User user) throws ConstraintViolationException;

	void delete(User user) throws ConstraintViolationException;

	void deleteAll() throws ConstraintViolationException;

	List<User> findAll() throws ConstraintViolationException;
     
}
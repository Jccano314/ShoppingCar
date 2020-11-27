package com.shoppingcar.service;

import java.util.List;

import com.shoppingcar.model.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User findById(Long id);
	
	public User save(User product);
	
	public void delete(Long id);
	
	public User findByNameAndPassword(String user,String pass);
}

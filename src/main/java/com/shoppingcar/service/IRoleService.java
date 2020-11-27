package com.shoppingcar.service;

import java.util.List;

import com.shoppingcar.model.Role;

public interface IRoleService {
	
	public List<Role> findAll();
	
	public Role findById(Long id);
	
	public Role save(Role product);
	
	public void delete(Long id);
}

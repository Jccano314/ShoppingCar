package com.shoppingcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcar.model.Role;
import com.shoppingcar.repository.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleRepository roleRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Role findById(Long id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		roleRepository.deleteById(id);
	}

}

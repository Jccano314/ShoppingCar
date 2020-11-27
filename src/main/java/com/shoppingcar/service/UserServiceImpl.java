package com.shoppingcar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcar.model.User;
import com.shoppingcar.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByNameAndPassword(String user, String pass) {
		return userRepository.findByNameAndPassword(user, pass);
	}

}

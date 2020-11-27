package com.shoppingcar.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingcar.model.User;

public interface IUserRepository extends CrudRepository<User, Long>{
	User findByNameAndPassword(String name,String pass);
}

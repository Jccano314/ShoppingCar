package com.shoppingcar.repository;

import org.springframework.data.repository.CrudRepository;

import com.shoppingcar.model.Product;

public interface IProductRepository extends CrudRepository<Product, Long>{
}

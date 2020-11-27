package com.shoppingcar.service;

import java.util.List;

import com.shoppingcar.model.Product;

public interface IProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);
	
	public Product save(Product product);
	
	public void delete(Long id);
}

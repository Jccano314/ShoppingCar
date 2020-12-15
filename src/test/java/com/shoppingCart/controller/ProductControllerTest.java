package com.shoppingCart.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.shoppingcar.controller.ProductController;
import com.shoppingcar.model.Product;
import com.shoppingcar.service.IProductService;

@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerTest {
	
	@Mock
	private IProductService productService;
	
	@InjectMocks
	private ProductController productController;
	
	@Mock
	BindingResult result;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@AfterEach
	public void tearDown() {
		System.out.println("end");
	}
	
	@Test
	@Order(1)
	public void verifyBuilderUserTest() {
		Product mockRole = this.builderProduct();
		assertEquals(mockRole.getId(), 9L);
	}
	
	@Test
	@Order(2)
	public void indexTest() {
		List<Product> mockProducts = this.builderProducts();
		when(productService.findAll()).thenReturn(mockProducts);
		List<Product> response = productController.index();
	    assertEquals(2, response.size());
	}
	
	@Test
	@Order(3)
	public void showTest() {
		Product mockProduct = this.builderProduct();
		when(productService.findById(9L)).thenReturn(mockProduct);
		productController.show(9L);
		verify(productService, times(1)).findById(9L);
	}
	
	@Test
	@Order(4)
	public void createTest(){
		Product mockProduct = this.builderProduct();
		mockProduct.setName("Play 5");
		when(productService.save(mockProduct)).thenReturn(mockProduct);
		when(result.hasErrors()).thenReturn(false);
		ResponseEntity<?> response = productController.create(mockProduct,result);
		assertEquals( HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@Order(5)
	public void updateTest(){
		Product mockProduct = this.builderProduct();
		mockProduct.setName("Xbox");
		when(productService.save(mockProduct)).thenReturn(mockProduct);
		ResponseEntity<?> response = productController.update(mockProduct, result, 1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	@Order(6)
	public void deleteTest(){
		ResponseEntity<?> response = productController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	public Product builderProduct() {
		Product mockProduct = new Product();
		mockProduct.setId(9L);
		mockProduct.setName("Test");
		mockProduct.setPrice(1000);
		mockProduct.setDataCreate(new Date());
		mockProduct.setState(true);
		
		return mockProduct;
	}
	
	public List<Product> builderProducts() {
		List<Product> products = new ArrayList<Product>(); 
		Product mockProduct1 = new Product();
		mockProduct1.setId(1L);
		mockProduct1.setName("Play 5");
		mockProduct1.setDataCreate(new Date());
		mockProduct1.setState(true);
		Product mockProduct2 = new Product();
		mockProduct2.setId(2L);
		mockProduct2.setName("Xbox series");
		mockProduct2.setDataCreate(new Date());
		mockProduct2.setState(true);
		products.add(mockProduct1);
		products.add(mockProduct2);
		
		return products;
	}

}

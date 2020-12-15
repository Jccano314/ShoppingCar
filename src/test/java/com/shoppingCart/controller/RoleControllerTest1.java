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

import com.shoppingcar.controller.RoleController;
import com.shoppingcar.model.Role;
import com.shoppingcar.service.IRoleService;

@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(OrderAnnotation.class)
public class RoleControllerTest1 {
	
	@Mock
	private IRoleService roleService;
	
	@InjectMocks
	private RoleController roleController;
	
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
		Role mockRole = this.builderRole();
		assertEquals(mockRole.getId(), 9L);
	}
	
	@Test
	@Order(2)
	public void indexTest() {
		List<Role> mockRoles = this.builderRoles();
		when(roleService.findAll()).thenReturn(mockRoles);
		List<Role> response = roleController.index();
	    assertEquals(2, response.size());
	}
	
	@Test
	@Order(3)
	public void showTest() {
		Role mockRole = this.builderRole();
		when(roleService.findById(9L)).thenReturn(mockRole);
		roleController.show(9L);
		verify(roleService, times(1)).findById(9L);
	}
	
	@Test
	@Order(4)
	public void createTest(){
		Role mockRole = this.builderRole();
		mockRole.setName("admin");
		when(roleService.save(mockRole)).thenReturn(mockRole);
		when(result.hasErrors()).thenReturn(false);
		ResponseEntity<?> response = roleController.create(mockRole,result);
		assertEquals( HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@Order(5)
	public void updateTest(){
		Role mockRole = this.builderRole();
		mockRole.setName("client");
		when(roleService.save(mockRole)).thenReturn(mockRole);
		ResponseEntity<?> response = roleController.update(mockRole, result, 1L);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	@Order(6)
	public void deleteTest(){
		ResponseEntity<?> response = roleController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	public Role builderRole() {
		Role mockRole = new Role();
		mockRole.setId(9L);
		mockRole.setName("Test");
		mockRole.setDataCreate(new Date());
		mockRole.setState(true);
		
		return mockRole;
	}
	
	public List<Role> builderRoles() {
		List<Role> roles = new ArrayList<Role>(); 
		Role mockRole1 = new Role();
		mockRole1.setId(1L);
		mockRole1.setName("Admin");
		mockRole1.setDataCreate(new Date());
		mockRole1.setState(true);
		Role mockRole2 = new Role();
		mockRole2.setId(2L);
		mockRole2.setName("Client");
		mockRole2.setDataCreate(new Date());
		mockRole2.setState(true);
		roles.add(mockRole1);
		roles.add(mockRole2);
		
		return roles;
	}

}

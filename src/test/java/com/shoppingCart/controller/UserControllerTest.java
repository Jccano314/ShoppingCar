package com.shoppingCart.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.shoppingcar.controller.UserController;
import com.shoppingcar.model.User;
import com.shoppingcar.service.IUserService;

@RunWith(MockitoJUnitRunner.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {
	
	@Mock
	private IUserService userService;
	
	@InjectMocks
	private UserController userController;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		System.out.println("arranco");
	}
	
	@AfterEach
	public void tearDown() {
		System.out.println("termina");
	}
	
	@Test
	@Order(1)
	public void verifyBuilderUserTest() {
		User mockUser = this.builderUser();
		assertEquals(mockUser.getId(), 7L);
	}
	
	@Test
	@Order(2)
	public void showTest() {
		User mockUser = this.builderUser();
		Mockito.when(userService.findById(7L)).thenReturn(mockUser);
		userController.show(7L);
		verify(userService, times(1)).findById(7L);
	}
	
	public User builderUser() {
		User mockUser = new User();
		mockUser.setId(7L);
		mockUser.setName("Test");
		mockUser.setDataCreate(new Date());
		mockUser.setState(true);
		
		return mockUser;
	}

}

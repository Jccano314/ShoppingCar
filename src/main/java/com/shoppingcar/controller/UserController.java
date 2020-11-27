package com.shoppingcar.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcar.model.User;
import com.shoppingcar.service.IUserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired(required = true)
	private IUserService userService;

	@GetMapping("/user")
	public List<User> index() {
		return userService.findAll();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		User user = null;
		Map<String, Object> response = new HashMap<>();
		try {
			user = userService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user == null) {
			response.put("mensaje", "El usuario ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody User user, BindingResult result) {
		User userLogIn = null;
		Map<String, Object> response = new HashMap<>();
		try {
			userLogIn = userService.findByNameAndPassword(user.getName(), user.getPassword());
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (userLogIn == null) {
			response.put("mensaje", "El usuario  o la contraseña son incorrectos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		return new ResponseEntity<User>(userLogIn, HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		User userNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			userNew = userService.save(user);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error", e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con éxito");
		response.put("User", userNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
		User userNow = userService.findById(id);
		User userUpdate = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (userNow == null) {
			response.put("mensaje",
					"Error: no se pudo editar,El usuario ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			userNow.setName(user.getName());
			userNow.setPassword(user.getPassword());
			userNow.setState(user.getState());
			userNow.setRole(user.getRole());
			userNow.setDataCreate(new Date());
			userUpdate = userService.save(userNow);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error", e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con éxito");
		response.put("user", userUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error", e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}

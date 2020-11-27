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

import com.shoppingcar.model.Role;
import com.shoppingcar.service.IRoleService;

@RestController
@RequestMapping("/api")
public class RoleController {
	
	@Autowired(required = true)
	private IRoleService roleService;
	
	@GetMapping("/role")
	public List<Role> index(){
		return roleService.findAll();
	}
	
	@GetMapping("/role/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Role role = null;
		Map<String, Object> response = new HashMap<>();
		try {
			role = roleService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(role == null) {
			response.put("mensaje", "El rol ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Role>(role,HttpStatus.OK);		
	}
	
	@PostMapping("/role")
	public ResponseEntity<?> create(@Valid @RequestBody Role role,BindingResult result) {
		Role roleNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			roleNew = roleService.save(role); 
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El rol ha sido creado con éxito");
		response.put("role", roleNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);	
	}
	
	@PutMapping("/role/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Role role,BindingResult result,@PathVariable Long id) {
		Role roleNow = roleService.findById(id);
		Role roleUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(roleNow == null) {
			response.put("mensaje", "Error: no se pudo editar,El rol ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			roleNow.setName(role.getName());
			roleNow.setState(role.getState());
			roleNow.setDataCreate(new Date());
			roleUpdate = roleService.save(roleNow);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El rol ha sido actualizado con éxito");
		response.put("role", roleUpdate);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/role/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			roleService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El rol ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}

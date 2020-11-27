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

import com.shoppingcar.model.Product;
import com.shoppingcar.service.IProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired(required = true)
	private IProductService productService;
	
	@GetMapping("/products")
	public List<Product> index(){
		return productService.findAll();
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Product product = null;
		Map<String, Object> response = new HashMap<>();
		try {
			product = productService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(product == null) {
			response.put("mensaje", "El producto ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product,HttpStatus.OK);		
	}
	
	@PostMapping("/products")
	public ResponseEntity<?> create(@Valid @RequestBody Product product,BindingResult result) {
		Product productNew = null;
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
			productNew = productService.save(product); 
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Producto ha sido creado con éxito");
		response.put("product", productNew);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);	
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Product product,BindingResult result,@PathVariable Long id) {
		Product productNow = productService.findById(id);
		Product productUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors",errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		if(productNow == null) {
			response.put("mensaje", "Error: no se pudo editar,El producto ID: " + id.toString() + " " + "No existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			productNow.setName(product.getName());
			productNow.setPrice(product.getPrice());
			productNow.setType(product.getType());
			productNow.setState(product.getState());
			productNow.setDataCreate(new Date());
			productUpdate = productService.save(productNow);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido actualizado con éxito");
		response.put("product", productUpdate);
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			productService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error",e.getMessage() + " " + e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}

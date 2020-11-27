package com.shoppingcar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "product")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;

    @NotEmpty( message = "* name is required")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Min(value = 0)
    @Column(name = "price", nullable = false)
    private float price;

    @NotEmpty(message = "*type is required ")
    @Column(name = "type", nullable = false,length = 64)
    private String type;
    
    @Column(name = "state", nullable = false)
    private Boolean state;
    
    @Column(name = "data_create", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCreate;
        
	@PrePersist
	public void prePersist() {
		dataCreate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Date getDataCreate() {
		return dataCreate;
	}

	public void setDataCreate(Date dataCreate) {
		this.dataCreate = dataCreate;
	}
	
	

}

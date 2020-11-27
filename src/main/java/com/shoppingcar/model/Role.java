package com.shoppingcar.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "role")
public class Role {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role",nullable = false)
    private Long id_role;
	
	@NotEmpty( message = "* name is required")
    @Column(name = "name", nullable = false, length = 64)
    private String name;
	
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
		return id_role;
	}

	public void setId(Long id) {
		this.id_role = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

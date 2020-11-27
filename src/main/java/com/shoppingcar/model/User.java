package com.shoppingcar.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private Long id;
	
	@NotEmpty( message = "* name is required")
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "password", nullable = false, length = 512)
    private String password;
    
    @Column(name = "state", nullable = false)
    private Boolean state;
    
    @Column(name = "data_create", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCreate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_role", updatable=true, insertable = true)
    private Role role;
    
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}

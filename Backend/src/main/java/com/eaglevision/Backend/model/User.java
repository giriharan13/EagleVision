package com.eaglevision.Backend.model;

import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name = "users",
	uniqueConstraints = @UniqueConstraint(columnNames = {"userId","userName"})
)
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	
	@Id
	@GeneratedValue
	private Integer userId;
	
	private String userName;
	
	private String phoneNumber;
	
	private Date dateOfBirth;
	
	@Formula("")
	private Integer age;
	
	User(){
		super();
		this.userId = 2;
	}
	
	public User(String userName, String phoneNumber, Date dateOfBirth) {
		super();
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.userId = 1;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getAge() {
		return age;
	}		

}

package com.eaglevision.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Ping {
	
	@Id
	@GeneratedValue
	private Integer pingId;
	
	public Ping() {
		super();
	}
	
	public Integer getPingId() {
		return pingId;
	}
	

}

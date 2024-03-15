package com.eaglevision.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Ping {
	
	@Id
	@GeneratedValue
	private Integer pingId;
	
	@JsonBackReference(value = "item-ping")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="item_id")
	private Item item;
	
	public Ping() {
		super(); 
	}
	
	public Ping(Item item) {
		this.item=item; 
	}
	public Integer getPingId() {
		return pingId;
	}
	

}

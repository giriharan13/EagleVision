package com.eaglevision.Backend.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Ping {

	@Id
	@GeneratedValue
	private Integer pingId;

	@JsonBackReference(value = "item-ping")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "item_id")
	private Item item;

	@NotNull(message = "creation date cannot be null")
	private Date creationDate;

	public Ping() {
		super();
		creationDate = new Date(System.currentTimeMillis());
	}

	public Ping(Item item) {
		this.item = item;
		creationDate = new Date(System.currentTimeMillis());
	}

	public Integer getPingId() {
		return pingId;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	

}

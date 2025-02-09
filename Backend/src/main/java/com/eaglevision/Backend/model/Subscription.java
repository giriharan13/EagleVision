package com.eaglevision.Backend.model;

import java.time.ZonedDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Subscription {
	
	@Id
	@GeneratedValue
	private Integer subsrcriptionId;
	
	private String subscriptionName;
	
	private ZonedDateTime subscriptionEndDateTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	
	public Subscription() {
		super();
		subscriptionEndDateTime = ZonedDateTime.now().plusMonths(1l);
	}
	
	public Subscription(String subscriptionName) {
		super();
		this.setSubscriptionName(subscriptionName);
		this.setSubscriptionEndDateTime(ZonedDateTime.now().plusMonths(1l));
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public ZonedDateTime getSubscriptionEndDateTime() {
		return subscriptionEndDateTime;
	}

	public void setSubscriptionEndDateTime(ZonedDateTime subscriptionEndDateTime) {
		this.subscriptionEndDateTime = subscriptionEndDateTime;
	}

	public Integer getSubsrcriptionId() {
		return subsrcriptionId;
	}

	public void setSubsrcriptionId(Integer subsrcriptionId) {
		this.subsrcriptionId = subsrcriptionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}

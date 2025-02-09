package com.eaglevision.Backend.dto;

import java.time.ZonedDateTime;

import com.eaglevision.Backend.model.Subscription;

public class SubscriptionDTO {
	
	private String subscriptionName;
	
	private ZonedDateTime subscriptionEndDateTime;
	
	public SubscriptionDTO() {
		
	}

	public SubscriptionDTO(String subscriptionName, ZonedDateTime subscriptionEndDateTime) {
		super();
		this.subscriptionName = subscriptionName;
		this.subscriptionEndDateTime = subscriptionEndDateTime;
	}
	
	public SubscriptionDTO(Subscription subscription) {
		this.subscriptionName = subscription.getSubscriptionName();
		this.subscriptionEndDateTime = subscription.getSubscriptionEndDateTime();
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
	
	
}

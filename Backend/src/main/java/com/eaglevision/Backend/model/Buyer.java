package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class Buyer extends User {

	@JsonManagedReference(value = "buyer-ping")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer",fetch = FetchType.LAZY,orphanRemoval = true)
	private List<BuyerCheckPing> pingHistory = new ArrayList<BuyerCheckPing>();

	@JsonManagedReference(value = "buyer-review")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer",fetch = FetchType.LAZY,orphanRemoval = true)
	private List<Review> reviews = new ArrayList<Review>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer", fetch = FetchType.LAZY,orphanRemoval = true)
	private List<EagleEye> setEagleEyes = new ArrayList<EagleEye>(); 

	public Buyer() {
		super();
	}

	public Buyer(String userName, String phoneNumber, Date dateOfBirth) {
		super(userName, phoneNumber, dateOfBirth);
	}

	public Buyer(List<BuyerCheckPing> pingHistory, List<Review> reviews) {
		super();
		this.pingHistory = pingHistory;
		this.reviews = reviews;
	}

	public Buyer(String userName, String phoneNumber, Date dateOfBirth, List<BuyerCheckPing> pingHistory,
			List<Review> reviews) {
		super(userName, phoneNumber, dateOfBirth);
		this.pingHistory = pingHistory;
		this.reviews = reviews;
	}

	public Buyer(String username, String password, String phoneNumber, Date dateOfBirth, List<Role> roles) {
		super(username, password, phoneNumber, dateOfBirth, roles);
	}

	public List<BuyerCheckPing> getPingHistory() {
		return pingHistory;
	}

	public void setPingHistory(List<BuyerCheckPing> pingHistory) {
		this.pingHistory = pingHistory;
	}

	public void addBuyerCheckPing(BuyerCheckPing buyerCheckPing) {
		this.pingHistory.add(buyerCheckPing);
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}

	public List<EagleEye> getSetEagleEyes() {
		return setEagleEyes;
	}

	public void setSetEagleEyes(List<EagleEye> setEagleEyes) {
		this.setEagleEyes = setEagleEyes;
	}
	
	

}

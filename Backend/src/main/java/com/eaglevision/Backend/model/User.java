package com.eaglevision.Backend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "userName" }))
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue
	private Integer userId;

	private String userName;

	private String password;

	private String phoneNumber;

	private Date dateOfBirth;

	@Formula("")
	private Integer age;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	@JoinTable(joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId"))
	private List<Role> roles;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Subscription> subscriptions = new ArrayList<Subscription>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private Subscription activeSubscription;
	
	@ManyToMany
	@JoinTable(name="user_liked_reviews",joinColumns = @JoinColumn(name="userId",referencedColumnName = "userId"),inverseJoinColumns = @JoinColumn(name="reviewId",referencedColumnName = "reviewId"))
	private Set<Review> likedReviews = new HashSet<Review>(); 
	
	@ManyToMany
	@JoinTable(name="user_disliked_reviews",joinColumns = @JoinColumn(name="userId",referencedColumnName = "userId"),inverseJoinColumns = @JoinColumn(name="reviewId",referencedColumnName = "reviewId"))
	private Set<Review> dislikedReviews = new HashSet<Review>(); 
	
	private String profilePictureName;
	
	private String profilePictureType;
	
	
	// Not a good idea but ok for now 
	@Lob
	@JsonIgnore
	private byte[] profilePictureData;
	
	@Transient
	private String profilePictureDataB64;
	
	@OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Notification> notifications = new ArrayList<Notification>();

	User() {
		super();
	}

	public User(String userName, String phoneNumber, Date dateOfBirth) {
		super();
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public User(String userName, String password, String phoneNumber, Date dateOfBirth, List<Role> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.roles = roles;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	public Set<Review> getLikedReviews() {
		return likedReviews;
	}

	public void setLikedReviews(Set<Review> likedReviews) {
		this.likedReviews = likedReviews;
	}

	public Set<Review> getDislikedReviews() {
		return dislikedReviews;
	}

	public void setDislikedReviews(Set<Review> dislikedReviews) {
		this.dislikedReviews = dislikedReviews;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getProfilePictureName() {
		return profilePictureName;
	}

	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
	}

	public String getProfilePictureType() {
		return profilePictureType;
	}

	public void setProfilePictureType(String profilePictureType) {
		this.profilePictureType = profilePictureType;
	}

	public byte[] getProfilePictureData() {
		return profilePictureData;
	}

	public void setProfilePictureData(byte[] profilePictureData) {
		this.profilePictureData = profilePictureData;
	}

	public String getProfilePictureDataB64() {
		return profilePictureDataB64;
	}

	public void setProfilePictureDataB64(String profilePictureDataB64) {
		this.profilePictureDataB64 = profilePictureDataB64;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	public void addNotification(Notification notification) {
		this.notifications.add(notification);
	}

	public Subscription getActiveSubscription() {
		return activeSubscription;
	}

	public void setActiveSubscription(Subscription activeSubscription) {
		this.activeSubscription = activeSubscription;
	}

}

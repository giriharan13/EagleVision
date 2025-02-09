package com.eaglevision.Backend.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue
	private Integer notificationId;
	
	private String title;
	
	private String content;
	
	private Boolean isRead;
	
	//private NotificationType notificationType;
	
	private Date creationDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User sentTo;
	
	public Notification() {
		super();
		this.creationDate = new Date(System.currentTimeMillis());
		this.isRead=false;
	}
	
	public Notification(String title,String content,User sentTo) {
		this.title=title;
		this.content=content;
	//	this.notificationType=notificationType;
		this.creationDate = new Date(System.currentTimeMillis());
		this.sentTo=sentTo;
		this.isRead = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

//	public NotificationType getNotificationType() {
//		return notificationType;
//	}
//
//	public void setNotificationType(NotificationType notificationType) {
//		this.notificationType = notificationType;
//	}

	public User getSentTo() {
		return sentTo;
	}

	public void setSentTo(User sentTo) {
		this.sentTo = sentTo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	
	
	
}

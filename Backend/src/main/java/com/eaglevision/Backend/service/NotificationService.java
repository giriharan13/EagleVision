package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Notification;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {
	
	private SimpMessagingTemplate messagingTemplate;
	
	private NotificationRepository notificationRepository;
	
	private UtilityService utilityService;
	
	@Autowired
	public NotificationService(SimpMessagingTemplate messagingTemplate,NotificationRepository notificationRepository,UtilityService utilityService) {
		this.messagingTemplate = messagingTemplate;
		this.notificationRepository = notificationRepository;
		this.utilityService = utilityService;
	}
	
	
	@Async
	public void sendNotificationToUser(String userName,Notification notification) {
		System.out.println("Sending to "+userName);
		this.messagingTemplate.convertAndSendToUser(userName,"/queue/notifications", notification);
	}
	
	@Async
	public void sendNotificationToUsers(List<User> users,Notification notification) {
		for(User user:users) {
			sendNotificationToUser(user.getUserName(), notification);
		}
	}

	public Notification create(Notification notification) {
		return this.notificationRepository.save(notification);
		
	}


	@Transactional
	public List<Notification> getAllUnreadNotifications(String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		
		return this.notificationRepository.findAllByIsReadAndSentTo_userId(false, userId);
	}


	@Transactional
	public List<Notification> getAllNotifications(String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		return this.notificationRepository.findAllBySentTo_userId(userId);
	}
	
	@Transactional
	public void markAllAsRead(String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		
		List<Notification> notifications = this.notificationRepository.findAllBySentTo_userId(userId);
		for(Notification notification:notifications) {
			notification.setIsRead(true);
		}
		this.notificationRepository.saveAll(notifications);
	}


	public void markAsRead(Integer notificationId, String authHeader) throws Exception {
		Integer userId = this.utilityService.getUserId(authHeader);
		Notification notification = this.notificationRepository.findById(notificationId).get();
		
		if(!userId.equals(notification.getSentTo().getUserId())) {
			throw new Exception("Not allowed to mark this review as read!");
		}
		
		notification.setIsRead(true);
		this.notificationRepository.save(notification);
	}


	public void sendTelegramNotificationToVendor(Notification notification) {
		
	}
	
	

}

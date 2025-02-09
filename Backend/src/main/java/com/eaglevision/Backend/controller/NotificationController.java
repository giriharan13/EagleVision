package com.eaglevision.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eaglevision.Backend.service.NotificationService;

import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping({"/api/secure","/api/bot"})
public class NotificationController {
	
	
	private NotificationService notificationService;
	
	
	
	@Autowired
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	
	@GetMapping("/notifications/unread")
	public ResponseEntity<Object> getAllUnreadNotifications(@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object unreadNotifications = this.notificationService.getAllUnreadNotifications(authHeader);
			return ResponseEntity.ok(unreadNotifications);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching unread notifications: "+ex.getMessage());
		}
	}
	
	@GetMapping("/notifications")
	public ResponseEntity<Object> getAllNotifications(@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object notifications = this.notificationService.getAllNotifications(authHeader);
			return ResponseEntity.ok(notifications);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching notifications: "+ex.getMessage());
		}
	}
	
	@PutMapping("/notifications/{notificationId}/read")
	public ResponseEntity<Object> markAsRead(@PathVariable Integer notificationId,@RequestHeader("Authorization") String authHeader){
		try {
			if(notificationId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.notificationService.markAsRead(notificationId,authHeader);
			return ResponseEntity.ok("Successfully marked as read.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error marking as read: "+ex.getMessage());
		}
	}
	
	@PutMapping("/notifications/read")
	public ResponseEntity<Object> markAllAsRead(@RequestHeader("Authorization") String authHeader){
		try {
			if( authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.notificationService.markAllAsRead(authHeader);
			return ResponseEntity.ok("Successfully marked as read.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error marking as read: "+ex.getMessage());
		}
	}
	
	
	
}

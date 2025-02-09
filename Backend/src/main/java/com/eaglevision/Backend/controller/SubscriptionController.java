package com.eaglevision.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.service.SubscriptionService;

@RestController
@RequestMapping({"/api/bot","/api/secure"})
public class SubscriptionController {
	
	private SubscriptionService subscriptionService;
	
	@Autowired
	public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}
	
	@PutMapping("/subscribe/{subscriptionName}")
	public ResponseEntity<Object> subscribe(@PathVariable String subscriptionName,@RequestHeader("Authorization") String authHeader) {
		try {
			if(subscriptionName==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			System.out.println(subscriptionName);
			Object result = subscriptionService.subscribe(subscriptionName,authHeader);
			return ResponseEntity.ok(result);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error occured while subscribing: "+ex.getMessage());
		}
	}
	
	@GetMapping("/activeSubscription")
	public ResponseEntity<Object> getActiveSubscription(@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object activeSubscriptionDTO = subscriptionService.getActiveSubscriptionDTO(authHeader);
			return ResponseEntity.ok(activeSubscriptionDTO);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error occurred while fetching active subscription : "+ex.getMessage());
		}
	}
	
	@GetMapping("/subscriptions")
	public ResponseEntity<Object> getAllSubscriptions(@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object subscriptionDTOs = subscriptionService.getAllSubscriptionDTOs(pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(subscriptionDTOs);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error occurred while fetching all subscriptions : "+ex.getMessage());
		}
	}
	
	
}

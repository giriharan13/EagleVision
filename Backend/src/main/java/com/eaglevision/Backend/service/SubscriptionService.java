package com.eaglevision.Backend.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.SubscriptionDTO;
import com.eaglevision.Backend.model.Subscription;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.SubscriptionRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
	
	private SubscriptionRepository subscriptionRepository;
	
	private UserService userService;
	
	private UtilityService utilityService;
	
	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository,UtilityService utilityService,UserService userService) throws Exception {
		this.subscriptionRepository = subscriptionRepository;
		this.userService = userService;
		this.utilityService = utilityService;
	}

	public Boolean subscribe(String subscriptionName, String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		User user = this.userService.getUserByUserId(userId);
		
		Subscription activeSubscription = this.getActiveSubscription(userId);
		
		if(roles.contains("VENDOR")) {
			if(!subscriptionName.equals("VENDOR+")) {
				throw new Exception("No such subscription exists");
			}
			
			if(activeSubscription!=null && activeSubscription.getSubscriptionName().equals(subscriptionName)) {
				throw new Exception("You are already subscribed to "+subscriptionName);
			}
			
			if(activeSubscription==null) {
					Subscription subscription = new Subscription(subscriptionName);
					List<Subscription> subscriptions = user.getSubscriptions();
					subscriptions.add(subscription);
					user.setSubscriptions(subscriptions);
					user.setActiveSubscription(subscription);
					subscription.setUser(user);
					user = userService.save(user);
			}
			else {
				throw new Exception("No such subscription exists");
			}
		}
		else if(roles.contains("BUYER")){
			if(activeSubscription!=null && activeSubscription.getSubscriptionName().equals(subscriptionName)) {
				throw new Exception("You are already subscribed to "+subscriptionName);
			}
			else if(activeSubscription!=null && subscriptionName.equals("ACE") && activeSubscription.getSubscriptionName().equals("GOD")) {
				throw new Exception("You are already subscribed to GOD");
			}
			
			if(subscriptionName.equals("ACE") || subscriptionName.equals("GOD")) {
				Subscription subscription = new Subscription(subscriptionName);
				List<Subscription> subscriptions = user.getSubscriptions();
				subscriptions.add(subscription);
				user.setSubscriptions(subscriptions);
				subscription.setUser(user);
				user.setActiveSubscription(subscription);
				user = userService.save(user);
			}
			else {
				throw new Exception("No such subscription exists");
			}
		}
		
		return true;
	}
	
	
	@Transactional
	public Page<Subscription> getAllSubscriptions(Integer pageNumber,Integer pageSize,String authHeader){
		Integer userId = this.utilityService.getUserId(authHeader);
		Pageable pageable;
		
		if(pageSize==0) {
			pageable = Pageable.unpaged();
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize);
		}
		
		return this.subscriptionRepository.findByUser_userId(userId,pageable);
	}

	public Subscription getActiveSubscription(String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		return this.getActiveSubscription(userId);
	}

	public Subscription getActiveSubscription(Integer userId) {
		User user = this.userService.getUserByUserId(userId);
		inValidateActiveSubscription(user);
		return user.getActiveSubscription();
	}
	
	

	public SubscriptionDTO getActiveSubscriptionDTO(String authHeader) {
		Subscription activeSubscription = this.getActiveSubscription(authHeader);
		
		SubscriptionDTO activeSubscriptionDTO = new SubscriptionDTO();
		
		if(activeSubscription==null) {
			activeSubscriptionDTO.setSubscriptionName("FREE");
		}
		else {
			activeSubscriptionDTO.setSubscriptionName(activeSubscription.getSubscriptionName());
			activeSubscriptionDTO.setSubscriptionEndDateTime(activeSubscription.getSubscriptionEndDateTime());
		}
		return activeSubscriptionDTO;
	}
	
	
	
	public void inValidateActiveSubscription(User user) {
		if(user.getActiveSubscription()!=null && user.getActiveSubscription().getSubscriptionEndDateTime().isBefore(ZonedDateTime.now())) {
			user.setActiveSubscription(null);
			this.userService.save(user);
		}
	}

	@Transactional
	public Page<SubscriptionDTO> getAllSubscriptionDTOs(Integer pageNumber, Integer pageSize, String authHeader) {
		
		Page<Subscription> subscriptions = getAllSubscriptions(pageNumber, pageSize,authHeader);
		Page<SubscriptionDTO> subscriptionDTOs = subscriptions.map((Subscription subscription)->new SubscriptionDTO(subscription));
		return subscriptionDTOs;
	}
	
	
}

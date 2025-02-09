package com.eaglevision.Backend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Role;
import com.eaglevision.Backend.model.Subscription;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.repository.UserRepository;

@Service
public class AuthorityUtilityService {
	
	private SubscriptionService subscriptionService;
	
	@Autowired
	public AuthorityUtilityService(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	public Collection<GrantedAuthority> getRoles(User user) {
    	List<Role> roles = user.getRoles();
        Collection<GrantedAuthority> userRoles = roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        
        Subscription activeSubscription = subscriptionService.getActiveSubscription(user.getUserId());

        if(activeSubscription!=null) userRoles.add(new SimpleGrantedAuthority(activeSubscription.getSubscriptionName()));

        
        return userRoles;
    }
}

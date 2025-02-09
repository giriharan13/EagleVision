package com.eaglevision.Backend.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.service.AuthorityUtilityService;
import com.eaglevision.Backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class WebSocketAuthenticationFilter extends OncePerRequestFilter{
	
	private JwtDecoder jwtDecoder;
	
	private AuthorityUtilityService authorityUtilityService;
	
	private UserService userService;

	@Autowired
	public WebSocketAuthenticationFilter(JwtDecoder jwtDecoder,AuthorityUtilityService authorityUtilityService,UserService userService) {
		this.jwtDecoder = jwtDecoder;
		this.authorityUtilityService = authorityUtilityService;
		this.userService = userService;
	} 
	
	public WebSocketAuthenticationFilter() {
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String requestUri = request.getRequestURI();
			
			if(requestUri.startsWith("/ws")) {
				String token =  request.getParameter("token");
				
				if(token!=null) {
					Jwt jwt = jwtDecoder.decode(token);
					
					String userName = jwt.getSubject();
					
				    User user = userService.findUserByUserName(userName);
					
					Authentication authentication =  new UsernamePasswordAuthenticationToken(userName, null, authorityUtilityService.getRoles(user));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					doFilter(request, response, filterChain);
				}
				else {
					throw new Exception("Invalid or expired token!");
				}
				
			}
			else {
				doFilter(request, response, filterChain);
			}
		}
		catch(Exception ex) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: "+ex.getMessage());
		}
		
		return;
	}
	
//	public Collection<GrantedAuthority> getRoles(User user) {
//    	List<Role> roles = user.getRoles();
//        Collection<GrantedAuthority> userRoles = roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//        
//        List<Subscription> activeSubscriptions = userService.findActiveSubscriptions(user.getUserId());
//        
//        for(Subscription subscription:activeSubscriptions) {
//        	userRoles.add(new SimpleGrantedAuthority(subscription.getSubscriptionName()));
//        }
//        
//        return userRoles;
//    }

}

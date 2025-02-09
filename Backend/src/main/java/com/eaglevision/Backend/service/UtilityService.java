package com.eaglevision.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {
	
	
	private final JwtDecoder jwtDecoder;
	
	@Autowired
	public UtilityService(JwtDecoder jwtDecoder) {
		this.jwtDecoder = jwtDecoder;
	}
	
	
	public String getRoles(String authHeader) {
		String token = authHeader.substring(7);
		Jwt decodedJwt = jwtDecoder.decode(token);
		return decodedJwt.getClaimAsString("scope");
	}
	
	public Integer getUserId(String authHeader) {
		String token = authHeader.substring(7);
		Jwt decodedJwt = jwtDecoder.decode(token);
		return Integer.parseInt(decodedJwt.getClaimAsString("userId"));
	}
	
	public Double getRadius(String roles) {
		System.out.println(roles);
		if(roles.contains("GOD")) {
			return Double.MAX_VALUE;
		}
		else if(roles.contains("ACE")) {
			return 150000.0;
		}
		return 60000.0;
	}
	
	public String convertToActualFieldName(String jpaFieldName) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<jpaFieldName.length();i++) {
			if(Character.isUpperCase(jpaFieldName.charAt(i))) {
				sb.append('_');
				sb.append(Character.toLowerCase(jpaFieldName.charAt(i)));
			}
			else sb.append(jpaFieldName.charAt(i));
			
		}
		return sb.toString();
	}


	public Integer getEagleEyesLimit(String roles) {
		if(roles.contains("GOD")) {
			return Integer.MAX_VALUE;
		}
		else if(roles.contains("ACE")) {
			return 20;
		}
		return 2;
	}

}

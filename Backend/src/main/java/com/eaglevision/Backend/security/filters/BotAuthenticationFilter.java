package com.eaglevision.Backend.security.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eaglevision.Backend.service.VendorService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BotAuthenticationFilter extends OncePerRequestFilter {

    private String botToken;

    private VendorService vendorService;

    public BotAuthenticationFilter(String botToken, VendorService vendorService) {
        this.botToken = botToken;
        this.vendorService = vendorService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	

        String requestUri = request.getRequestURI();
        
        System.out.println(requestUri);

        if (!requestUri.startsWith("/api/bot")) {
            doFilter(request, response, filterChain);
            return;
        }

        String requestBotToken = request.getHeader("Bot-Token");
        String phoneNumber = request.getHeader("Phone-Number");
        if (requestBotToken != null && phoneNumber != null) {
            if (validateBotToken(requestBotToken) && validatePhoneNumber(phoneNumber)) {
                doFilter(request, response, filterChain);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    Boolean validateBotToken(String botToken) {
        return this.botToken.equals(botToken);
    }

    Boolean validatePhoneNumber(String phoneNumber) {
        return vendorService.existsByPhoneNumber(phoneNumber);
    }

}

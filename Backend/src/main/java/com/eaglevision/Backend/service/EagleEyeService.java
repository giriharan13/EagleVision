package com.eaglevision.Backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.EagleEye;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.repository.EagleEyeRepository;

@Service
public class EagleEyeService {
	
	private EagleEyeRepository eagleEyeRepository;
	
	
	private BuyerService buyerService;
	
	private UtilityService utilityService; 
	
	
	@Autowired
	public EagleEyeService(EagleEyeRepository eagleEyeRepository,BuyerService buyerService,UtilityService utilityService) {
		this.eagleEyeRepository=eagleEyeRepository;
		this.buyerService = buyerService;
		this.utilityService = utilityService;
	}
	
	public void removeEagleEye(EagleEye eagleEye) {
		eagleEyeRepository.delete(eagleEye);
	}
	
	public EagleEye getByBuyerAndItem(Integer buyerId,Integer itemId,String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR") || !buyerId.equals(userId)) {
			throw new Exception("Not allowed to fetch eagle eyes.");
		}
		
		EagleEye eagleEye = this.eagleEyeRepository.findByBuyer_userIdAndItem_itemId(buyerId, itemId);
		
		if(eagleEye == null) {
			throw new Exception("No eagle eye set on this item by the user.");
		}
		
		return eagleEye;
	}

}

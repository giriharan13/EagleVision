package com.eaglevision.Backend.service;

import java.util.List;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.UserDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.repository.BuyerRepository;

@Service
public class BuyerService {

	private BuyerRepository buyerRepository;
	
	private UtilityService utilityService;

	@Autowired
	public BuyerService(BuyerRepository buyerRepository, UtilityService utilityService) {
		this.buyerRepository = buyerRepository;
		this.utilityService = utilityService;
	}

	public List<Buyer> getBuyers() {
		return buyerRepository.findAll();
	}

	public UserDTO getUserDTOById(Integer id,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("A vendor cannot access a buyer's profile.");
		}
		
		Buyer buyer = buyerRepository.findById(id).orElse(null);
		UserDTO userDTO = new UserDTO(buyer);
		return userDTO;
	}
	

	public Buyer getBuyerById(Integer id,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("A vendor cannot access a buyer's profile.");
		}
		
		Buyer buyer = buyerRepository.findById(id).orElse(null);
		return buyer;
	}

	public List<Review> getAllReviews(Integer id) {
		return buyerRepository.findById(id).get().getReviews();
	}

	public Buyer createBuyer(Buyer buyer) {
		buyer = buyerRepository.save(buyer);
		return buyer;
	}

	public Buyer updateBuyer(Buyer updatedBuyer, Integer userId,String authHeader) throws Exception{
		Integer currentUserId = utilityService.getUserId(authHeader);
		
		if(!currentUserId.equals(userId)) {
			throw new Exception("You are not allowed to update.");
		}
		
		Buyer buyer = buyerRepository.findById(userId).get();
		buyer.setPhoneNumber(updatedBuyer.getPhoneNumber());
		buyer.setUserName(updatedBuyer.getUserName());
		buyer.setDateOfBirth(updatedBuyer.getDateOfBirth());
		buyer.setPassword(updatedBuyer.getPassword());
		buyerRepository.save(buyer);
		return buyer;
	}

	public void deleteBuyer(Integer userId,String authHeader) throws Exception {
		Integer currentUserId = utilityService.getUserId(authHeader);
		
		if(currentUserId!=userId) {
			throw new Exception("You are not allowed to update.");
		}
		buyerRepository.deleteById(userId);
	}
	
	

}

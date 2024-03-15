package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.repository.BuyerRepository;

@Service
public class BuyerService {
	
    private BuyerRepository buyerRepository;
	
	@Autowired
	public BuyerService(BuyerRepository buyerRepository) {
		this.buyerRepository = buyerRepository;
	}
	
	public List<Buyer> getBuyers(){
		return buyerRepository.findAll();
	}
	
	public Buyer getBuyerById(Integer id) {
		return buyerRepository.findById(id).orElse(null);
	}
	
	public List<Review> getAllReviews(Integer id){
		return buyerRepository.findById(id).get().getReviews();
	}
	
	public Buyer createBuyer(Buyer buyer) {
		buyerRepository.save(buyer);
		return buyer;
	}
	
	public Buyer updateBuyer(Buyer updatedBuyer,Integer userId) {
		Buyer buyer = buyerRepository.findById(userId).get();
		buyer.setPhoneNumber(updatedBuyer.getPhoneNumber());
		buyer.setUserName(updatedBuyer.getUserName());
		buyer.setDateOfBirth(updatedBuyer.getDateOfBirth());
		buyerRepository.save(buyer);
		return buyer;
	}
	
	public void deleteBuyer(Integer id) {
		buyerRepository.deleteById(id);
	}
	
}

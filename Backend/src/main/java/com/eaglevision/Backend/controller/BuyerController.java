package com.eaglevision.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.service.BuyerService;


@RestController
@RequestMapping("/users/buyers")
public class BuyerController {
	
	
	private BuyerService buyerService;
	
	@Autowired
	public BuyerController(BuyerService buyerService) {
		this.buyerService = buyerService;
	}
	
	@GetMapping
	public List<Buyer> getBuyers(){
		return buyerService.getBuyers(); 
	}
	
	@GetMapping("/{userId}")
	public Buyer getBuyerById(@PathVariable Integer userId) {
		return buyerService.getBuyerById(userId);
	}
	
	@GetMapping("/{userId}/reviews")
	public List<Review> getAllReviews(@PathVariable Integer userId){
		return buyerService.getAllReviews(userId);
	}
	
	@PostMapping
	public ResponseEntity<Buyer> createBuyer(@RequestBody Buyer buyer) {
		return ResponseEntity.ok(buyerService.createBuyer(buyer));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Buyer> updateBuyer(@RequestBody Buyer buyer,@PathVariable Integer userId) {
		return ResponseEntity.ok(buyerService.updateBuyer(buyer,userId));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteBuyer(@PathVariable Integer userId) {
		buyerService.deleteBuyer(userId);
		return ResponseEntity.ok("User deleted successfully");
	}
}

package com.eaglevision.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.service.BuyerService;

@RestController
// @RequestMapping("/users/buyers")
@RequestMapping({ "/api/secure/users/buyers", "/api/bot/users/buyers" })
public class BuyerController {

	private BuyerService buyerService;

	@Autowired
	public BuyerController(BuyerService buyerService) {
		this.buyerService = buyerService;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getBuyerById(@PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object buyer = buyerService.getUserDTOById(userId,authHeader);
			return ResponseEntity.ok(buyer);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching user: "+ex.getMessage());
		}
	}

//	@GetMapping("/{userId}/reviews")
//	public ResponseEntity<Object> getAllReviews(@PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
//		try {
//			if(userId==null || authHeader==null) {
//				return ResponseEntity.badRequest().body("Invalid input data.");
//			}
//			Object reviews = buyerService.getAllReviews(userId,authHeader);
//			return ResponseEntity.ok(reviews);
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//			return ResponseEntity.internalServerError().body("Error fetching reviews the reviews of a buyer: "+ex.getMessage());
//		}
//	}

	@PostMapping
	public ResponseEntity<Object> createBuyer(@RequestBody Buyer buyer) {
		try {
			if(buyer==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object createdBuyer = buyerService.createBuyer(buyer);
			return ResponseEntity.ok(createdBuyer);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating a buyer: "+ex.getMessage());
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateBuyer(@RequestBody Buyer buyer, @PathVariable Integer userId,@RequestHeader("Authorization") String authHeader)
	{
		try {
			if(buyer==null || userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedBuyer = buyerService.updateBuyer(buyer, userId,authHeader);
			return  ResponseEntity.ok(updatedBuyer);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating the buyer: "+ex.getMessage());
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteBuyer(@PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			buyerService.deleteBuyer(userId,authHeader);
			return ResponseEntity.ok("User deleted successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting the buyer: "+ex.getMessage());
		}
	}
}

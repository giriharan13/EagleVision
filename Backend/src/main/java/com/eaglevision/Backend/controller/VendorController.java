package com.eaglevision.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.service.VendorService;
@RestController
@RequestMapping({ "/api/bot/users/vendors", "/api/secure/users/vendors" })
public class VendorController {

	private VendorService vendorService;

	@Autowired
	public VendorController(VendorService vendorService) {
		this.vendorService = vendorService;
	}

//	@GetMapping
//	public List<Vendor> getVendors() {
//		return vendorService.getVendors();
//	}

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getVendorById(@PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			
			Object vendor = vendorService.getUserDTOById(userId,authHeader);
			return ResponseEntity.ok(vendor);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching vendor: "+ex.getMessage());
			
		}
	}

	@GetMapping("/optedin/{phoneNumber}")
	public ResponseEntity<Boolean> checkIfOptedId(@PathVariable String phoneNumber) {
		Vendor vendor = vendorService.getVendorByPhoneNumber(phoneNumber);
		return new ResponseEntity<>(vendor.getOptedForTelegramNotifications(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> createVendor(@RequestBody Vendor vendor) {
		try {
			if(vendor==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object createdVendor = this.vendorService.createVendor(vendor);
			return ResponseEntity.ok(createdVendor);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting vendor: "+ex.getMessage());
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Object> updateVendor(@RequestBody Vendor vendor, @PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(vendor==null || userId==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedVendor = this.vendorService.updateVendor(vendor, userId,authHeader);
			return ResponseEntity.ok(updatedVendor);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating vendor: "+ex.getMessage());
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteVendor(@PathVariable Integer userId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			vendorService.deleteVendor(userId,authHeader);
			return ResponseEntity.ok("Vendor deleted successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting vendor: "+ex.getMessage());
		}
	}

}

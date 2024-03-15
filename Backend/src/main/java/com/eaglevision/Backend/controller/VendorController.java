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

import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.service.VendorService;
import com.eaglevision.Backend.service.bridge.ShopVendorService;

@RestController
@RequestMapping("/users/vendors")
public class VendorController {
	
	private VendorService vendorService;
	
	private ShopVendorService shopVendorService;
	
	@Autowired
	public VendorController(VendorService vendorService,ShopVendorService shopVendorService) {
		this.vendorService = vendorService;
		this.shopVendorService = shopVendorService;
	}

	@GetMapping
	public List<Vendor> getVendors(){
		return vendorService.getVendors(); 
	}
	
	@GetMapping("/{userId}")
	public Vendor getVendorById(@PathVariable Integer userId) {
		return vendorService.getVendorById(userId);
	}
	
	@GetMapping("/{userId}/shops")
	public List<Shop> getAllShops(@PathVariable Integer userId){
		return shopVendorService.getAllShopsByUserId(userId);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
		return ResponseEntity.ok(this.vendorService.createVendor(vendor));
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor vendor,@PathVariable Integer userId) {
		return ResponseEntity.ok(this.vendorService.updateVendor(vendor,userId));
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteVendor(@PathVariable Integer userId) {
		vendorService.deleteVendor(userId);
		return ResponseEntity.ok("Vendor deleted successfully");
	}
	
	
	
	

}

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

import com.eaglevision.Backend.dto.CreateShopDTO;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.service.ShopService;
import com.eaglevision.Backend.service.bridge.VendorShopService;

@RestController
@RequestMapping
public class ShopController {

	private ShopService shopService;

	private VendorShopService vendorShopService;

	@Autowired
	public ShopController(ShopService shopService, VendorShopService vendorShopService) {
		this.shopService = shopService;
		this.vendorShopService = vendorShopService;
	}

	@PostMapping(value = "/shops")
	public ResponseEntity<Shop> createShop(@RequestBody CreateShopDTO createShopRequest) {
		Vendor vendor = this.vendorShopService.getVendorById(createShopRequest.getUserId());
		return ResponseEntity.ok(shopService.createShop(createShopRequest, vendor));
	}

	@GetMapping("/users/vendors/{vendorId}/ownedshops")
	public List<Shop> getAllShopsByVendor(@PathVariable Integer vendorId) {
		return this.shopService.getAllShopsByVendor(vendorId);
	}

	@GetMapping("/users/vendors/{vendorId}/ownedshops/{shopId}")
	public Shop getShopByVendor(@PathVariable Integer vendorId, @PathVariable Integer shopId) {
		return this.shopService.getShopByVendor(vendorId, shopId);
	}

	@GetMapping("/shops")
	public List<Shop> getAllShops() {
		return this.shopService.getAllShops();
	}

	@GetMapping("/shops/{shopId}")
	public Shop getShopById(@PathVariable Integer shopId) {
		return this.shopService.getShopById(shopId);
	}

	@DeleteMapping("/shops/{shopId}")
	public ResponseEntity<String> deleteShopById(@PathVariable Integer shopId) {
		this.shopService.deleteShopById(shopId);
		return ResponseEntity.ok("Shop with id=" + shopId + " deleted successfully!");
	}

	@PutMapping(value = "/shops/{shopId}")
	public ResponseEntity<Shop> updateShop(@PathVariable Integer shopId, @RequestBody Shop shop) {
		return ResponseEntity.ok(this.shopService.updateShop(shopId, shop));
	}

}

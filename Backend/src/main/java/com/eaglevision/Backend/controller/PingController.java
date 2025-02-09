package com.eaglevision.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.dto.CreatePingDTO;
import com.eaglevision.Backend.service.PingService;

@RestController
@RequestMapping({ "/api/bot", "/api/secure" })
public class PingController {

	private PingService pingService;

	@Autowired
	public PingController(PingService pingService){
		this.pingService = pingService;
	}

	@PostMapping(value = "/shops/{shopId}/items/{itemId}/pings")
	public ResponseEntity<Object> createBuyerCheckPing(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@RequestBody CreatePingDTO createPingDTO,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader) {
//		Item item = this.itemPingService.getItemForPing(shopId, itemId);
//		if (createPingDTO.getType() == 0)
//			createPingDTO.setBuyer(this.buyerPingService.getBuyerForPing(createPingDTO.getUserId()));
//		else
//			createPingDTO.setVendor(this.vendorPingService.getVendorForPing(createPingDTO.getUserId()));
//		return ResponseEntity.ok(this.pingService.createPing(item, createPingDTO));
		try {
			if(shopId==null || itemId==null || createPingDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object createdBuyerCheckPingDTO = this.pingService.createBuyerCheckPing(shopId,itemId, createPingDTO,lat,lon,authHeader);
			return ResponseEntity.ok(createdBuyerCheckPingDTO);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating the ping: "+ex.getMessage());
		}
	}

	@PostMapping(value = "/shops/{shopId}/items/{itemId}/pings/{pingId}")
	public ResponseEntity<Object> createVendorResponsePing(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@PathVariable Integer pingId, @RequestBody CreatePingDTO createPingDTO,@RequestHeader("Authorization") String authHeader) {
//		System.out.println(itemId + " " + pingId + " " + shopId);
//		VendorResponsePing vendorResponsePing = this.pingService.createVendorResponsePing(shopId, itemId, pingId,
//				createPingDTO);
//		return ResponseEntity.ok(vendorResponsePing);
		try {
			if(shopId==null || itemId==null || createPingDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object createdVendorResponsePing = this.pingService.createVendorResponsePing(shopId,itemId,pingId, createPingDTO,authHeader);
			return ResponseEntity.ok(createdVendorResponsePing);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating the ping: "+ex.getMessage());
		}
		
	}

	@GetMapping(value = "/shops/{shopId}/items/{itemId}/pings")
	public ResponseEntity<Object> getAllBuyerCheckPings(@PathVariable Integer shopId,
			@PathVariable Integer itemId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestHeader("Authorization") String authHeader) {
//		return this.pingService.getAllBuyerCheckPings(itemId).stream()
//				.map((BuyerCheckPing buyerCheckPing) -> new BuyerCheckPingDTO(buyerCheckPing)).toList();
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object buyerCheckPingDTOs = this.pingService.getAllBuyerCheckPings(shopId,itemId,lat,lon,pageNumber,pageSize,authHeader);
			
			return ResponseEntity.ok(buyerCheckPingDTOs);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching the ping: "+ex.getMessage());
		}
		
	}

//	@GetMapping(value = "/shops/{shopId}/items/{itemId}/pings/{pingId}")
//	public BuyerCheckPingDTO getBuyerCheckPingById(@PathVariable Integer shopId, @PathVariable Integer itemId,
//			@PathVariable Integer pingId) {
////		Ping ping = this.pingService.getPing(itemId, pingId);
////		if (ping instanceof BuyerCheckPing) {
////			BuyerCheckPing buyerCheckPing = (BuyerCheckPing) ping;
////			return new BuyerCheckPingDTO(buyerCheckPing);
////		} else {
////			VendorResponsePing vendorResponsePing = (VendorResponsePing) ping;
////			return new BuyerCheckPingDTO(vendorResponsePing);
////		}
//		
//		try {
//			
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//			return ResponseEntity.internalServerError().body("Error fetching buyerCheckpings")
//		}
//	}
//	
	@GetMapping(value = "/pings/vendors/{vendorId}")
	public ResponseEntity<Object> getAllPingsOnVendor(@PathVariable Integer vendorId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue="0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestHeader("Authorization") String authHeader){
		try {
			if(vendorId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object buyerCheckPingDTOs =  this.pingService.getAllPingsOnVendor(vendorId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(buyerCheckPingDTOs);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching pings on vendor: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/pings/vendors/response/{vendorId}")
	public ResponseEntity<Object> getAllResponsePingsByVendor(@PathVariable Integer vendorId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue="0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestHeader("Authorization") String authHeader){
		try {
			if(vendorId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object buyerCheckPingDTOs =  this.pingService.getAllResponsePingsByVendor(vendorId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(buyerCheckPingDTOs);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching pings on vendor: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/pings/buyers/{buyerId}")
	public ResponseEntity<Object> getAllPingsByBuyer(@PathVariable Integer buyerId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue="0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestHeader("Authorization") String authHeader){
		try {
			if(buyerId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object buyerCheckPingDTOs =  this.pingService.getAllPingsByBuyer(buyerId,lat,lon,pageNumber,pageSize,authHeader);
			return ResponseEntity.ok(buyerCheckPingDTOs);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching pings by buyer: "+ex.getMessage());
		}
	}
	
	
	
//	@PostMapping(value = "/pings/reply/{pingId}")
//	public ResponseEntity<Ping> createVendorPing2(@PathVariable Integer pingId,@RequestBody CreatePingDTO createPingDTO){
//		VendorResponsePing vendorResponsePing = this.pingService.createVendorResponsePing(pingId,
//				createPingDTO);
//		
//		return ResponseEntity.ok(vendorResponsePing);
//	}
}

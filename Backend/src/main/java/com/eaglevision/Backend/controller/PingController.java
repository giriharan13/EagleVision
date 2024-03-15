package com.eaglevision.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Ping;
import com.eaglevision.Backend.requests.CreatePingRequest;
import com.eaglevision.Backend.service.PingService;
import com.eaglevision.Backend.service.bridge.BuyerPingService;
import com.eaglevision.Backend.service.bridge.ItemPingService;
import com.eaglevision.Backend.service.bridge.VendorPingService;

@RestController
@RequestMapping
public class PingController {
	
	private PingService pingService;
	
	private ItemPingService itemPingService;
	
	private BuyerPingService buyerPingService;
	
	private VendorPingService vendorPingService;
	
	@Autowired
	public PingController(PingService pingService,ItemPingService itemPingService,BuyerPingService buyerPingService,VendorPingService vendorPingService) {
		this.pingService = pingService;
		this.itemPingService = itemPingService;
		this.buyerPingService = buyerPingService;
		this.vendorPingService = vendorPingService;
	}
	
	
	@PostMapping(value="/shops/{shopId}/items/{itemId}/pings")
	public ResponseEntity<Ping> createPing(@PathVariable Integer shopId,@PathVariable Integer itemId,@RequestBody CreatePingRequest createPingRequest){
		Item item = this.itemPingService.getItemForPing(shopId, itemId);
		if(createPingRequest.getType()==0) createPingRequest.setBuyer(this.buyerPingService.getBuyerForPing(createPingRequest.getUserId()));
		else createPingRequest.setVendor(this.vendorPingService.getVendorForPing(createPingRequest.getUserId()));
		return ResponseEntity.ok(this.pingService.createPing(item,createPingRequest));
	}
	
	@GetMapping(value="/shops/{shopId}/items/{itemId}/pings")
	public List<Ping> getAllPings(@PathVariable Integer shopId,@PathVariable Integer itemId){
		return this.pingService.getAllPings(itemId);
	}
	
	@GetMapping(value="/shops/{shopId}/items/{itemId}/pings/{pingId}")
	public Ping getAllPings(@PathVariable Integer shopId,@PathVariable Integer itemId,@PathVariable Integer pingId){
		return this.pingService.getPing(itemId,pingId);
	}
}

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

import com.eaglevision.Backend.dto.BuyerCheckPingDTO;
import com.eaglevision.Backend.dto.CreatePingDTO;
import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Ping;
import com.eaglevision.Backend.model.VendorResponsePing;
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
	public PingController(PingService pingService, ItemPingService itemPingService, BuyerPingService buyerPingService,
			VendorPingService vendorPingService) {
		this.pingService = pingService;
		this.itemPingService = itemPingService;
		this.buyerPingService = buyerPingService;
		this.vendorPingService = vendorPingService;
	}

	@PostMapping(value = "/shops/{shopId}/items/{itemId}/pings")
	public ResponseEntity<Ping> createPing(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@RequestBody CreatePingDTO createPingRequest) {
		Item item = this.itemPingService.getItemForPing(shopId, itemId);
		if (createPingRequest.getType() == 0)
			createPingRequest.setBuyer(this.buyerPingService.getBuyerForPing(createPingRequest.getUserId()));
		else
			createPingRequest.setVendor(this.vendorPingService.getVendorForPing(createPingRequest.getUserId()));
		return ResponseEntity.ok(this.pingService.createPing(item, createPingRequest));
	}

	@PostMapping(value = "/shops/{shopId}/items/{itemId}/pings/{pingId}")
	public ResponseEntity<Ping> createVendorPing(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@PathVariable Integer pingId, @RequestBody CreatePingDTO createPingDTO) {
		System.out.println(itemId + " " + pingId + " " + shopId);
		VendorResponsePing vendorResponsePing = this.pingService.createVendorResponsePing(shopId, itemId, pingId,
				createPingDTO);
		return ResponseEntity.ok(vendorResponsePing);
	}

	@GetMapping(value = "/shops/{shopId}/items/{itemId}/pings")
	public List<BuyerCheckPingDTO> getAllBuyerCheckPings(@PathVariable Integer shopId,
			@PathVariable Integer itemId) {
		return this.pingService.getAllBuyerCheckPings(itemId).stream()
				.map((BuyerCheckPing buyerCheckPing) -> new BuyerCheckPingDTO(buyerCheckPing)).toList();
	}

	@GetMapping(value = "/shops/{shopId}/items/{itemId}/pings/{pingId}")
	public BuyerCheckPingDTO getPingById(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@PathVariable Integer pingId) {
		Ping ping = this.pingService.getPing(itemId, pingId);
		if (ping instanceof BuyerCheckPing) {
			BuyerCheckPing buyerCheckPing = (BuyerCheckPing) ping;
			return new BuyerCheckPingDTO(buyerCheckPing);
		} else {
			VendorResponsePing vendorResponsePing = (VendorResponsePing) ping;
			return new BuyerCheckPingDTO(vendorResponsePing);
		}
	}
}

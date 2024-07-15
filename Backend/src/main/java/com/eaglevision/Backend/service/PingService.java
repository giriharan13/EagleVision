package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.CreatePingDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Ping;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.model.VendorResponsePing;
import com.eaglevision.Backend.repository.BuyerCheckPingRepository;
import com.eaglevision.Backend.repository.PingRepository;

@Service
public class PingService {

	private PingRepository pingRepository;

	private ItemService itemService;

	private VendorService vendorService;

	private BuyerCheckPingRepository buyerCheckPingRepository;

	@Autowired
	public PingService(PingRepository pingRepository, ItemService itemService, VendorService vendorService,
			BuyerCheckPingRepository buyerCheckPingRepository) {
		this.pingRepository = pingRepository;
		this.itemService = itemService;
		this.vendorService = vendorService;
		this.buyerCheckPingRepository = buyerCheckPingRepository;
	}

	public Ping createPing(Item item, CreatePingDTO createPingRequest) {
		if (createPingRequest.getType() == 0) {
			Buyer buyer = createPingRequest.getBuyer();
			BuyerCheckPing buyerCheckPing = new BuyerCheckPing(buyer, item);
			return this.pingRepository.save(buyerCheckPing);
		} else {
			Vendor vendor = createPingRequest.getVendor();
			VendorResponsePing vendorResponsePing = new VendorResponsePing(createPingRequest.getQuantity(), item,
					vendor);
			return this.pingRepository.save(vendorResponsePing);
		}
	}

	public List<Ping> getAllPings(Integer itemId) {
		return this.pingRepository.findByItem_itemId(itemId);
	}

	public List<BuyerCheckPing> getAllBuyerCheckPings(Integer itemId) {
		return this.buyerCheckPingRepository.findByItem_itemId(itemId);
	}

	public Ping getPing(Integer itemId, Integer pingId) {
		return this.pingRepository.findByItem_itemIdAndPingId(itemId, pingId);
	}

	public VendorResponsePing createVendorResponsePing(Integer shopId, Integer itemId, Integer pingId,
			CreatePingDTO createPingDTO) {
		VendorResponsePing vendorResponsePing = new VendorResponsePing(createPingDTO.getQuantity(),
				itemService.getItem(shopId, itemId), vendorService.getVendorById(createPingDTO.getUserId()));
		BuyerCheckPing buyerCheckPing = (BuyerCheckPing) pingRepository.findById(pingId).get();
		buyerCheckPing.setVendorResponsePing(vendorResponsePing);
		pingRepository.save(vendorResponsePing);
		pingRepository.save(buyerCheckPing);
		return vendorResponsePing;
	}
}

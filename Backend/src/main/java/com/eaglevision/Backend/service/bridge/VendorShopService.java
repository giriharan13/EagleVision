package com.eaglevision.Backend.service.bridge;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.VendorRepository;
import com.eaglevision.Backend.requests.CreateShopRequest;

@Service
public class VendorShopService {
	
	private VendorRepository vendorRepository;

	@Autowired
	public VendorShopService(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	public Vendor getVendorById(Integer userId) {
		return vendorRepository.findById(userId).get();
	}
	
	public Shop addVendorForShop(CreateShopRequest createShopRequest) {
		Vendor vendor = this.getVendorById(createShopRequest.getUserId());
		Shop shop = new Shop(createShopRequest.getShopName(),
							createShopRequest.getContactNumber(),
							createShopRequest.getAddress(),
							createShopRequest.getHours(),
							vendor
							);
		for(Item item:createShopRequest.getItems()) {
			shop.addItem(item);
		}
		return shop;
	}
	
	public Vendor updateVendorForShop(Shop shop) {
		Vendor vendor = shop.getVendor();
		vendor.addShop(shop);
		return this.vendorRepository.save(vendor);
	}
}

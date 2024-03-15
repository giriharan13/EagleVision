package com.eaglevision.Backend.service.bridge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.repository.ShopRepository;

@Service
public class ShopVendorService {
	
	private ShopRepository shopRepository;
	
	@Autowired
	public ShopVendorService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}
	
	public List<Shop> getAllShopsByUserId(Integer userId){
		return shopRepository.findShopsByVendor_userId(userId);
	}
	
	

}

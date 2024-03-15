package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.service.ShopService;

@Service
public class ShopItemService {
	
	private ShopService shopService;
	
	@Autowired
	public ShopItemService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	public Shop getShopForItem(Integer shopId) {
		return this.shopService.getShopById(shopId);
	}
}

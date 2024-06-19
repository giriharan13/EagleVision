package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.repository.ShopRepository;

@Service
public class ShopReviewService {

	private ShopRepository shopRepository;

	@Autowired
	public ShopReviewService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	public Shop getShopById(Integer shopId) {
		return this.shopRepository.findById(shopId).get();
	}

}

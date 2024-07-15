package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.dto.CreateShopDTO;
import com.eaglevision.Backend.dto.ShopDTO;
import com.eaglevision.Backend.dto.ShopReviewDTO;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.ShopRepository;

@Service
public class ShopService {

	private ShopRepository shopRepository;

	private VendorService vendorService;

	@Autowired
	public ShopService(ShopRepository shopRepository, VendorService vendorService) {
		this.shopRepository = shopRepository;
		this.vendorService = vendorService;
	}

	public Shop getShopById(Integer id) {
		return shopRepository.findById(id).orElse(null);
	}

	public ShopDTO getShopDTOById(Integer id) {
		Shop shop = shopRepository.findById(id).orElse(null);
		ShopDTO shopDTO = new ShopDTO(shop);

		for (ShopReview shopReview : shop.getShopReviews()) {
			shopDTO.addShopReview(new ShopReviewDTO(shopReview));
		}

		return shopDTO;
	}

	public Integer getOwnerId(Integer shopId) {
		return shopRepository.findById(shopId).get().getVendor().getUserId();
	}

	public Shop getShopByVendor(Integer vendorId, Integer shopId) {
		return shopRepository.findShopByVendor_userIdAndShopId(vendorId, shopId);
	}

	public List<Shop> getAllShops() {
		return this.shopRepository.findAll();
	}

	public List<Shop> getAllShopsByVendor(Integer vendorId) {
		return this.shopRepository.findShopsByVendor_userId(vendorId);
	}

	public Shop createShop(CreateShopDTO createShopRequest) {
		Vendor vendor = vendorService.getVendorByName(createShopRequest.getUserName());
		Shop shop = new Shop(createShopRequest.getShopName(),
				createShopRequest.getDescription(),
				createShopRequest.getContactNumber(),
				createShopRequest.getAddress(),
				createShopRequest.getHours(),
				vendor);
		if (createShopRequest.getItems() != null) {
			for (Item item : createShopRequest.getItems()) {
				shop.addItem(item);
			}
		}
		return shopRepository.save(shop);
	}

	public Shop updateShop(Integer shopId, Shop updatedShop) {
		Shop shop = shopRepository.findById(shopId).get();
		shop.setShopName(updatedShop.getShopName());
		shop.setDescription(updatedShop.getDescription());
		shop.setContactNumber(updatedShop.getContactNumber());
		shop.updateAddress(updatedShop.getAddress());
		shop.updateHours(updatedShop.getHours());
		return shopRepository.save(shop);
	}

	public void deleteShopById(Integer id) {
		shopRepository.deleteById(id);
	}

}

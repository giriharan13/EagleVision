package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.ShopRepository;
import com.eaglevision.Backend.requests.CreateShopRequest;

@Service
public class ShopService {
	
	private ShopRepository shopRepository;
	
	
	@Autowired
	public ShopService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}
	
	public Shop getShopById(Integer id) {
		return shopRepository.findById(id).orElse(null);
	}
	
	public Shop getShopByVendor(Integer vendorId,Integer shopId) {
		return shopRepository.findShopByVendor_userIdAndShopId(vendorId,shopId);
	}
	
	public List<Shop> getAllShops(){
		return this.shopRepository.findAll();
	}
	
	public List<Shop> getAllShopsByVendor(Integer vendorId){
		return this.shopRepository.findShopsByVendor_userId(vendorId);
	}
	
	public Shop createShop(CreateShopRequest createShopRequest,Vendor vendor) {
		Shop shop = new Shop(createShopRequest.getShopName(),
				createShopRequest.getDescription(),
				createShopRequest.getContactNumber(),
				createShopRequest.getAddress(),
				createShopRequest.getHours(),
				vendor
				);
		for(Item item:createShopRequest.getItems()) {
			shop.addItem(item);
		}
		return shopRepository.save(shop);
	}
	
	
	public Shop updateShop(Integer shopId,Shop updatedShop) {
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

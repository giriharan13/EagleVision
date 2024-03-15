package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.repository.ItemRepository;

@Service
public class ItemPingService {
	
	private ItemRepository itemRepository;
	
	@Autowired
	public ItemPingService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Item getItemForPing(Integer shopId,Integer itemId) {
		return this.itemRepository.findByShop_shopIdAndItemId(shopId, itemId);
	}
}

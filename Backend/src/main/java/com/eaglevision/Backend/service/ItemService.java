package com.eaglevision.Backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.repository.ItemRepository;

@Service
public class ItemService {
	
	private ItemRepository itemRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Item createItem(Item item) {
		return itemRepository.save(item);
	}
	
	public Item getItem(Integer shopId,Integer itemId) {
		return itemRepository.findByShop_shopIdAndItemId(shopId, itemId);
	}
	
	public List<Item> getAllItems(Integer itemId){
		return itemRepository.findByShop_shopId(itemId);
	}
	
	public Item updateItem(Item item) {
		return itemRepository.save(item);
	}
	
	public void deleteItemById(Integer itemId) {
		itemRepository.deleteById(itemId);
	}
	
}

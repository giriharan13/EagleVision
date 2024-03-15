package com.eaglevision.Backend.service.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.repository.ItemRepository;

@Service
public class ItemReviewService {
	
	private ItemRepository itemRepository;
	
	@Autowired
	public ItemReviewService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Item getItemForReview(Integer itemId) {
		return this.itemRepository.findById(itemId).get();
	}

}

package com.eaglevision.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.service.ItemService;
import com.eaglevision.Backend.service.bridge.ShopItemService;

@RestController
@RequestMapping(value = "/")
public class ItemController {
	
	private ItemService itemService;
	
	private ShopItemService shopItemService;
	
	@Autowired
	public ItemController(ItemService itemService,ShopItemService shopItemService)
	{
		this.itemService = itemService;
		this.shopItemService = shopItemService;
	}
	
	@GetMapping(value = "shops/{shopId}/items/{itemId}")
	public Item getItem(@PathVariable Integer shopId,@PathVariable Integer itemId){
		return this.itemService.getItem(shopId,itemId);
	}
	
	@GetMapping(value = "shops/{shopId}/items")
	public List<Item> getAllItems(@PathVariable Integer shopId){
		return this.itemService.getAllItems(shopId);
	}
	
	@PostMapping(value = "shops/{shopId}/items")
	public ResponseEntity<Item> createItem(@PathVariable Integer shopId,@RequestBody Item item){
		Shop shop = this.shopItemService.getShopForItem(shopId);
		shop.addItem(item);
		return ResponseEntity.ok(this.itemService.createItem(item));
	}
	
	@PutMapping(value = "shops/{shopId}/items/{itemId}")
	public ResponseEntity<Item> updateItem(@PathVariable Integer shopId,@PathVariable Integer  itemId,@RequestBody Item updatedItem){
		Item item = this.itemService.getItem(shopId, itemId);
		item.setItemName(updatedItem.getItemName());
		item.setItemPrice(updatedItem.getItemPrice());
		return ResponseEntity.ok(this.itemService.updateItem(item));
	}
	
	@DeleteMapping(value = "shops/{shopId}/items/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable Integer shopId,@PathVariable Integer itemId) {
		this.itemService.deleteItemById(itemId);
		return ResponseEntity.ok("Item deleted successfully");
	}
	
	

}

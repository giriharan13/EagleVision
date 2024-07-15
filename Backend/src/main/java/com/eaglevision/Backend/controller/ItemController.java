package com.eaglevision.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eaglevision.Backend.dto.ItemDTO;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.service.ItemService;

@RestController
@RequestMapping(value = "/")
public class ItemController {

	private ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping(value = "shops/{shopId}/items/{itemId}")
	public ItemDTO getItem(@PathVariable Integer shopId, @PathVariable Integer itemId) {
		return new ItemDTO(this.itemService.getItem(shopId, itemId));
	}

	@GetMapping(value = "shops/{shopId}/items")
	public List<Item> getAllItems(@PathVariable Integer shopId) {
		return this.itemService.getAllItems(shopId);
	}

	@PostMapping(value = "shops/{shopId}/items")
	public ResponseEntity<String> createItem(@PathVariable Integer shopId, @RequestBody Item item) {
		item = this.itemService.createItem(item, shopId);
		if (item != null)
			return ResponseEntity.ok("Item created successfully!");
		return new ResponseEntity<>("Error occurred when creating the item", HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "shops/{shopId}/items/{itemId}")
	public ResponseEntity<Item> updateItem(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@RequestBody Item updatedItem) {
		Item item = this.itemService.getItem(shopId, itemId);
		item.setItemName(updatedItem.getItemName());
		item.setItemPrice(updatedItem.getItemPrice());
		return ResponseEntity.ok(this.itemService.updateItem(item));
	}

	@DeleteMapping(value = "shops/{shopId}/items/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable Integer shopId, @PathVariable Integer itemId) {
		this.itemService.deleteItemById(itemId);
		return ResponseEntity.ok("Item deleted successfully");
	}

}

package com.eaglevision.Backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.service.ItemService;

@RestController
@RequestMapping({ "/api/bot", "/api/secure" })
public class ItemController {

	private ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

//	@GetMapping(value = "/{shopId}/items/{itemId}")
//	public ItemDTO getItem(@PathVariable Integer shopId, @PathVariable Integer itemId) {
//		return new ItemDTO(this.itemService.getItem(shopId, itemId));
//	}
	
	@GetMapping(value = "/shops/{shopId}/items/{itemId}")
	public ResponseEntity<Object> getItem(@PathVariable Integer shopId, @PathVariable Integer itemId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || itemId==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object itemDTO = itemService.getItemDTO(shopId, itemId,lat,lon,authHeader);
			return ResponseEntity.ok(itemDTO);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching item: "+ex.getMessage());
		}
	}

	@GetMapping(value = "/shops/{shopId}/items")
	public ResponseEntity<Object> getAllItemsByShop(@PathVariable Integer shopId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue = "")String query,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestParam(defaultValue = "itemName") String sortBy,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object items = itemService.getAllItemDTOsByShop(shopId,lat,lon,query,pageNumber,pageSize,sortBy,authHeader);
			return ResponseEntity.ok(items);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching items: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/searchitems")
	public ResponseEntity<Object> getAllItems(@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue = "")String query,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestParam(defaultValue = "itemName") String sortBy,@RequestHeader("Authorization") String authHeader) {
		try {
			if(authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object items = itemService.getAllItemOverviewDTOs(lat,lon,query,pageNumber,pageSize,sortBy,authHeader);
			return ResponseEntity.ok(items);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching items: "+ex.getMessage());
		}
	}

	@PostMapping(value = "/shops/{shopId}/items")
	public ResponseEntity<Object> createItem(@PathVariable Integer shopId, @RequestPart Item item,@RequestPart MultipartFile itemImageFile,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object createdItem = this.itemService.createItem(item, shopId,itemImageFile,authHeader);
			return ResponseEntity.ok(createdItem);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating item: "+ex.getMessage());
		}

	}

	@PutMapping(value = "/shops/{shopId}/items/{itemId}")
	public ResponseEntity<Object> updateItem(@PathVariable Integer shopId, @PathVariable Integer itemId,
			@RequestPart Item item,@RequestPart(required = false) MultipartFile itemImageFile,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedItem = this.itemService.updateItem(item,shopId,itemId,itemImageFile,authHeader);
			return ResponseEntity.ok(updatedItem);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating item: "+ex.getMessage());
		}
//		Item item = this.itemService.getItem(shopId, itemId);
//		item.setItemName(updatedItem.getItemName());
//		item.setItemPrice(updatedItem.getItemPrice());
	}

	@DeleteMapping(value = "/shops/{shopId}/items/{itemId}")
	public ResponseEntity<Object> deleteItem(@PathVariable Integer shopId, @PathVariable Integer itemId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.itemService.deleteItemById(shopId,itemId,authHeader);
			return ResponseEntity.ok("Item deleted successfully");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting item: "+ex.getMessage());
		}
	}
	
	@PutMapping(value = "/shops/{shopId}/items/{itemId}/setEagleEye")
	public ResponseEntity<Object> setEagleEye(@PathVariable Integer shopId, @PathVariable Integer itemId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.itemService.setEagleEyes(shopId,itemId,lat,lon,authHeader);
			return ResponseEntity.ok("Set Eagle Eye on the item.");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error setting eagle eyes: "+ex.getMessage());
		}
	}
	
	@PutMapping(value = "/shops/{shopId}/items/{itemId}/removeEagleEye")
	public ResponseEntity<Object> removeEagleEye(@PathVariable Integer shopId, @PathVariable Integer itemId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || itemId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.itemService.removeEagleEyes(shopId,itemId,lat,lon,authHeader);
			return ResponseEntity.ok("Removed Eagle Eye on the item");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error removing eagle eyes: "+ex.getMessage());
		}
	}
	
	@GetMapping(value = "/items/eagleEyes")
	public ResponseEntity<Object> getAllItemsUnderEagleEyesOfUser(@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data");
			}
			Object items = this.itemService.getAllItemsUnderEagleEyesOfUser(lat,lon,authHeader);
			return ResponseEntity.ok(items);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching eagle eyes detail: "+ex.getMessage());
		}
	}

	
}

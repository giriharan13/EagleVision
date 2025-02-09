package com.eaglevision.Backend.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.dto.CreateShopDTO;
import com.eaglevision.Backend.dto.ShopDTO;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.service.ShopService;

@RestController
@RequestMapping({ "/api/bot", "api/secure" })
public class ShopController {

	private ShopService shopService;

	@Autowired
	public ShopController(ShopService shopService) {
		this.shopService = shopService;
	}
	
	/*
	 * Creates a new shop.
	 * 
	 * @param createShopDTO - Data Transfer Object for the new shop.
	 * @param shopImageFile - Image of the new shop.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return Response Entity containing the newly created shop or an error message. 
	 * 
	 */

	@PostMapping(value = "/shops")
	public ResponseEntity<Object> createShop(@RequestPart CreateShopDTO createShopDTO,@RequestPart MultipartFile shopImageFile,@RequestHeader("Authorization") String authHeader){
		try {
			if(createShopDTO==null || shopImageFile==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object createdShop = shopService.createShop(createShopDTO,shopImageFile,authHeader);
			return ResponseEntity.ok(createdShop);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error creating shop: "+ex.getMessage());
		}
	}
	
	/*
	 * Fetches all the shops owned by the vendor.
	 * 
	 * @param vendorId - User ID of the vendor.
	 * @param lat - Latitude of the user.
	 * @param lon - Longitude of the user.
	 * @param pageNumber - Page number of the page requested.
	 * @param pageSize - Number of records per page.
	 * @param sortBy - Field based on which the records should be sorted.(JPA format)
	 * @param query - Search query of the user.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return all the shops from the vendor within the vicinity based on query, sorted or an error message.
	 * 
	 */
	
	@GetMapping("/users/vendors/{vendorId}/ownedshops")
	public ResponseEntity<Object> getAllShopsByVendor(@PathVariable Integer vendorId,@RequestParam(name="lat",required = true) Double lat,@RequestParam(name="lon", required = true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "5") Integer pageSize,@RequestParam(defaultValue = "shopName") String sortBy,@RequestParam(defaultValue="") String query,@RequestHeader("Authorization") String authHeader){
		try {
			if(vendorId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shops = this.shopService.getAllShopDTOsByVendor(vendorId,lat,lon,pageNumber,pageSize,sortBy,query,authHeader);
			return ResponseEntity.ok(shops);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shops : "+ex.getMessage());
		}
	}
	
	/*
	 * Fetches the shop with the given shop ID.
	 * 
	 * @param vendorId - User ID of the vendor.
	 * @param shopId - ID of the shop.
	 * @param lat - Latitude of the user.
	 * @param lon - Longitude of the user.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return the shop with the given shop ID or an error message.
	 * 
	 */
	
	@GetMapping("/users/vendors/{vendorId}/ownedshops/{shopId}")
	public ResponseEntity<Object> getShopByVendor(@PathVariable Integer vendorId, @PathVariable Integer shopId,@RequestParam(name="lat",required = true) Double lat,@RequestParam(name="lon", required = true) Double lon,@RequestHeader("Authorization") String authHeader) {
		try {
			if(vendorId == null || shopId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopDTO = shopService.getShopDTOByVendor(vendorId, shopId,lat,lon,authHeader);
			return ResponseEntity.ok(shopDTO);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop : "+ex.getMessage());
		}
	}
	
	/*
	 * Fetches all the shops.
	 * 
	 * @param lat - Latitude of the user.
	 * @param lon - Longitude of the user.
	 * @param pageNumber - Page number of the page requested.
	 * @param pageSize - Number of records per page.  
	 * @param sortBy - Field based on which the records should be sorted.
	 * @param query - Search query of the user.
	 * @param category - Category of the shops to be fetched.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return all the shops within the vicinity based on query,category sorted or an error message.
	 * 
	 */
	
	@GetMapping("/shops")
	public ResponseEntity<Object> getAllShops(@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "0") Integer pageSize,@RequestParam(defaultValue = "shopName") String sortBy,@RequestParam(defaultValue = "") String query,@RequestParam(defaultValue = "ALL") String category,@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shops = this.shopService.getAllShops(query,category,pageNumber,pageSize,sortBy,lat,lon,authHeader);
			return ResponseEntity.ok(shops);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shops : "+ex.getMessage());
		}
	}
	
	
	/*
	 * Fetches a shop with shop ID.
	 * 
	 * @param shopId - ID of the shop.
	 * @param lat - Latitude of the user.
	 * @param lon - Longitude of the user.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return shop with the given shop ID if it is within the vicinity or an error message.
	 * 
	 */
	
	@GetMapping("/shops/{shopId}")
	public ResponseEntity<Object> getShopById(@PathVariable Integer shopId,@RequestParam(required = true) Double lat,@RequestParam(required = true) Double lon,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || authHeader==null ) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopDTO = this.shopService.getShopDTOById(shopId,lat,lon,authHeader);
			return ResponseEntity.ok(shopDTO);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop : "+ex.getMessage());
		}
	}
	
	/*
	 * Updates the shop with the shop ID.
	 * 
	 * @param shopId - ID of the shop.
	 * @param updateShopDTO - Shop entity object with the updated fields.
	 * @param shopImageFile - Updated shop image.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return updated shop or an error message.
	 * 
	 */
	
	@PutMapping(value = "/shops/{shopId}")
	public ResponseEntity<Object> updateShop(@PathVariable Integer shopId,@RequestPart Shop updatedShopDTO,@RequestPart(required = false) MultipartFile shopImageFile,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || updatedShopDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatedShop = this.shopService.updateShop(shopId, updatedShopDTO,shopImageFile,authHeader);
			return ResponseEntity.ok(updatedShop);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating shop : "+ex.getMessage());
		}
	}
	
	
	
	/*
	 * Deletes a shop with the shop ID.
	 * 
	 * @param shopId - ID of the shop.
	 * @param authHeader - Authorization header of the user.
	 * 
	 * @return success message with the ID of the shop deleted or an error message 
	 * 
	 */
	
	@DeleteMapping("/shops/{shopId}")
	public ResponseEntity<Object> deleteShopById(@PathVariable Integer shopId,@RequestHeader("Authorization") String authHeader) {
		try {
			if(shopId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			this.shopService.deleteShopById(shopId,authHeader);
			return ResponseEntity.ok("Shop with id:" + shopId + " deleted successfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error deleting shop : "+ex.getMessage());
		}
	}
	
	/*
	 * Fetches the user ID of the owner of the shop.
	 * 
	 * @param shopId - ID of the shop.
	 * 
	 * @return userId of the owner of the shop or an error message.
	 * 
	 */
	
	@GetMapping("/shops/{shopId}/owner")
	public ResponseEntity<Object> getOwnerId(@PathVariable Integer shopId) {
		try {
			if(shopId==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object ownerId = shopService.getOwnerId(shopId);
			return ResponseEntity.ok(ownerId);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching owner ID : "+ex.getMessage());
		}
	}
	
	@PutMapping("/shops/{shopId}/setShopMarker")
	public ResponseEntity<Object> setShopMarker(@PathVariable Integer shopId,@RequestPart MultipartFile markerImageFile,@RequestHeader("Authorization") String authHeader){
		try {
			if(shopId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object updatesShop = shopService.setShopMarker(shopId,markerImageFile,authHeader);
			return ResponseEntity.ok(updatesShop);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error setting shop marker.");
		}
	}
	
	@GetMapping("/shops/{shopId}/shopMarker")
	public ResponseEntity<Object> getShopMarker(@PathVariable Integer shopId,@RequestHeader("Authorization")String authHeader){
		try {
			if(shopId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object shopMarker = shopService.getShopMarker(shopId,authHeader);
			return ResponseEntity.ok(shopMarker);
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error fetching shop marker: "+ex.getMessage());
		}
	}
	
	
	/*
	 * Old  unprofessional Code
	 * 
	 */
	
	

//	@GetMapping("/users/vendors/{vendorId}/ownedshops")
//	public Page<ShopDTO> getAllShopsByVendor(@PathVariable Integer vendorId,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "5") Integer pageSize,@RequestParam(defaultValue = "shopName") String sortBy,@RequestParam(defaultValue="") String query) {
//		Page<Shop> shops = this.shopService.getAllShopsByVendor(vendorId,pageNumber,pageSize,sortBy,query);
//		return shops.map((shop)-> new ShopDTO(shop));
//	}

//	@GetMapping("/users/vendors/{vendorId}/ownedshops/{shopId}")
//	public ShopDTO getShopByVendor(@PathVariable Integer vendorId, @PathVariable Integer shopId) {
//		return new ShopDTO(shopService.getShopByVendor(vendorId, shopId));
//	}

//	@GetMapping("/shops")
//	public List<Shop> getAllShops(@RequestParam(required = false) String query,@RequestParam(name="radius") Double radius,@RequestParam(name="lat") Double lat,@RequestParam(name="lon") Double lon){
//		System.out.print(radius+" "+lat+" "+lon);
//		if(query == null || query.equals("all")) return this.shopService.getAllShops(radius,lat,lon);
//		return this.shopService.getShopsByQuery(query,radius,lat,lon);
//	}
//
//	@GetMapping("/shops/{shopId}")
//	public ShopDTO getShopById(@PathVariable Integer shopId) {
//		return new ShopDTO(this.shopService.getShopById(shopId));
//	}

//	@GetMapping("/shops/{shopId}/owner")
//	public Integer getOwnerId(@PathVariable Integer shopId) {
//		return this.shopService.getOwnerId(shopId);
//	}

//	@DeleteMapping("/shops/{shopId}")
//	public ResponseEntity<String> deleteShopById(@PathVariable Integer shopId) {
//		this.shopService.deleteShopById(shopId);
//		return ResponseEntity.ok("Shop with id=" + shopId + " deleted successfully!");
//	}

//	@PutMapping(value = "/shops/{shopId}")
//	public ResponseEntity<Shop> updateShop(@PathVariable Integer shopId,@RequestPart Shop updateShopRequest,@RequestPart(required = false) MultipartFile shopImageFile) throws IOException {
//		return ResponseEntity.ok(this.shopService.updateShop(shopId, updateShopRequest,shopImageFile));
//	}
	
//	@GetMapping(value="/shops/category")
//	public Page<Shop> getShopsByCategory(@RequestParam(name="longitude") Double longitude,@RequestParam(name="latitude") Double latitude,@RequestParam(name="radius") Double radius,@RequestParam(name="type") ShopCategory shopCategory,@RequestParam(defaultValue = "0") Integer pageNumber,@RequestParam(defaultValue = "1") Integer pageSize){
//		return this.shopService.getAllShopsByShopCategory(longitude,latitude,radius,shopCategory,pageNumber,pageSize);
//	}
	
	

}

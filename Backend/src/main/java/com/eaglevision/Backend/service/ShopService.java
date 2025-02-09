package com.eaglevision.Backend.service;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.dto.CreateShopDTO;
import com.eaglevision.Backend.dto.ShopDTO;
import com.eaglevision.Backend.dto.ShopMarkerDTO;
import com.eaglevision.Backend.dto.ShopReviewDTO;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Review;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.model.ShopCategory;
import com.eaglevision.Backend.model.ShopReview;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.ShopRepository;
import com.eaglevision.Backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ShopService {

	private ShopRepository shopRepository;
	
	private UserRepository userRepository;

	private VendorService vendorService;
	
	private UtilityService utilityService;
	
	private SubscriptionService subscriptionService;
	

	@Autowired
	public ShopService(ShopRepository shopRepository,UserRepository userRepository, VendorService vendorService,UtilityService utilityService,SubscriptionService subscriptionService) {
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
		this.vendorService = vendorService;
		this.utilityService = utilityService;
		this.subscriptionService = subscriptionService;
	}
	
	public Shop createShop(CreateShopDTO createShopRequest,MultipartFile shopImageFile,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(!roles.contains("VENDOR")) { 
			throw new Exception("Not allowed to create shops");
		}
		
		Vendor vendor = vendorService.getVendorByName(createShopRequest.getUserName());

		Shop shop = new Shop(createShopRequest.getShopName(),
				createShopRequest.getDescription(),
				createShopRequest.getContactNumber(),
				createShopRequest.getAddress(),
				createShopRequest.getHours(),
				vendor,createShopRequest.getShopCategory(),createShopRequest.getShopLocation());
		shop.setImageName(shopImageFile.getName());
		shop.setImageType(shopImageFile.getContentType());
		shop.setShopImageData(shopImageFile.getBytes());
		
		return shopRepository.save(shop);
	}
	
	public Page<Shop> getAllShopsByVendor(Integer vendorId,Double lat,Double lon,int pageNumber,int pageSize,String sortBy,String query,String authContext) throws Exception {
		String roles = utilityService.getRoles(authContext);
		Integer userId = utilityService.getUserId(authContext);
		Double radius = utilityService.getRadius(roles);
		
		Pageable page=null;
		
		if(pageSize == 0) {
			page = Pageable.unpaged(Sort.by(sortBy));
		}
		
		System.out.println(userId+" "+vendorId);
		
		if(roles.contains("VENDOR")) {
			if(page==null) page = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
			if(userId.equals(vendorId)) {
				Page<Shop> shops = this.shopRepository.findShopsByVendor_userIdAndShopNameContainingIgnoreCase(vendorId,query, page);
				return this.encodeImage(shops);
			}
			throw new Exception("Not allowed to fetch shops.");
		}
		
		sortBy=utilityService.convertToActualFieldName(sortBy);
		if(page==null) page = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Page<Shop> shops = shopRepository.findShopsByVendor(vendorId, lon, lat, radius, query, page);
		return this.encodeImage(shops);
	}
	
	public Page<ShopDTO> getAllShopDTOsByVendor(Integer vendorId,Double lat,Double lon,int pageNumber,int pageSize,String sortBy,String query,String authContext) throws Exception {
		return this.mapShopsToShopDTOs(this.getAllShopsByVendor(vendorId, lat, lon, pageNumber, pageSize, sortBy, query, authContext));
	}
	
	
	
	public Shop getShopByVendor(Integer vendorId, Integer shopId,Double lat,Double lon,String authHeader) throws Exception {
		Integer userId = utilityService.getUserId(authHeader);
		String roles = utilityService.getRoles(authHeader);
		
		if(userId.equals(vendorId)) {
			Shop shop = shopRepository.findShopByVendor_userIdAndShopId(vendorId, shopId);
			return this.encodeImage(shop);
		}
		
		Double radius = utilityService.getRadius(roles);
		Shop shop = shopRepository.findShopByVendor(vendorId, shopId,lon,lat,radius);
		return this.encodeImage(shop);
	}
	
	public ShopDTO getShopDTOByVendor(Integer vendorId, Integer shopId,Double lat,Double lon,String authHeader) throws Exception {
		Integer userId = utilityService.getUserId(authHeader);
		String roles = utilityService.getRoles(authHeader);
		
		if(userId.equals(vendorId)) {
			Shop shop = shopRepository.findShopByVendor_userIdAndShopId(vendorId, shopId);
			return new ShopDTO(this.encodeImage(shop));
		}
		
		Double radius = utilityService.getRadius(roles);
		Shop shop = shopRepository.findShopByVendor(vendorId, shopId,lon,lat,radius);
		return new ShopDTO(this.encodeImage(shop));
	}



	public ShopDTO getShopDTOById(Integer shopId,Double lat,Double lon,String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		if(roles.contains("VENDOR")) {
			Integer userId = utilityService.getUserId(authHeader);
			Integer ownerId = getOwnerId(shopId);
			
			if(userId.equals(ownerId)) {
				Shop shop = shopRepository.findById(shopId).orElse(null);
				return new ShopDTO(this.encodeImage(shop));
			}
			
			throw new Exception("Not allowed to fetch this shop.");
		}
		
		Shop shop =  shopRepository.findShop(shopId,lon,lat,radius);
		return new ShopDTO(this.encodeImage(shop));
	}
	
	public Shop getShopById(Integer shopId,Double lat,Double lon,String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		if(roles.contains("VENDOR")) {
			Integer userId = utilityService.getUserId(authHeader);
			Integer ownerId = getOwnerId(shopId);
			
			if(userId.equals(ownerId)) {
				return this.encodeImage(shopRepository.findById(shopId).orElse(null));
				
			}
			
			throw new Exception("Not allowed to fetch this shop.");
		}
		
		return this.encodeImage(shopRepository.findShop(shopId,lon,lat,radius));
	}
	
	public Shop getShopById(Integer id) {
		Shop shop = shopRepository.findById(id).orElse(null);
		return this.encodeImage(shop);
	}
	

	public ShopDTO getShopDTOById(Integer id) {
		Shop shop = shopRepository.findById(id).orElse(null);
		ShopDTO shopDTO = new ShopDTO(shop);

//		for (ShopReview shopReview : shop.getShopReviews()) {
//			shopDTO.addShopReview(new ShopReviewDTO(shopReview));
//		}

		return shopDTO;
	}

	public Integer getOwnerId(Integer shopId) {
		return shopRepository.findById(shopId).get().getVendor().getUserId();
	}


	public List<Shop> getAllShops() {
		List<Shop> shops = this.shopRepository.findAll();
		return this.encodeImage(shops);
	}
	
	public Page<Shop> getAllShops(String query,String category,Integer pageNumber,Integer pageSize,String sortBy,Double lat,Double lon,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		sortBy = utilityService.convertToActualFieldName(sortBy);
		
		if(pageSize>10) {
			throw new Exception("Not allowed to fetch more than 10 shops per page.");
		}
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to fetch all shops.");
		}
		
		Pageable pageable;
		if(pageSize==0) {
			pageable = Pageable.unpaged(Sort.by(sortBy));
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		}
		
		if(category.equals("ALL")) {
			Page<Shop> shops = this.shopRepository.findAll(query,lon,lat,radius,pageable);
			this.invalidateAndRemoveMarkers(shops);
			return this.encodeImage(shops);
		}
		Page<Shop> shops = this.shopRepository.findAll(query,ShopCategory.valueOf(category).ordinal(),lon,lat,radius,pageable);
		this.invalidateAndRemoveMarkers(shops);
		return this.encodeImage(shops);
	}
	
	public List<Shop> getShopsByQuery(String query){
		List<Shop> shops = this.shopRepository.findShopsByQuery(query);
		return this.encodeImage(shops);
	}
	
	public List<Shop> getShopsByQuery(String query,Double radius,Double lat,Double lon){
		List<Shop> shops = this.shopRepository.findShopsByQuery(query,radius,lat,lon);
		return this.encodeImage(shops);
	}

	
	public Page<Shop> getAllShopsByShopCategory(Double longitude,Double latitude,Double radius,ShopCategory shopCategory,Integer pageNumber,Integer pageSize){
		Pageable pageable = PageRequest.of(pageNumber,pageSize);
		Page<Shop> shops  = this.shopRepository.findShopsByShopCategory(longitude,latitude,radius,shopCategory,pageable);
		return this.encodeImage(shops);
	}

	public Shop updateShop(Integer shopId, Shop updatedShop,MultipartFile shopImageData,String authHeader) throws Exception {
		Integer userId = utilityService.getUserId(authHeader);
		Integer ownerId = getOwnerId(shopId);
		
		if(!userId.equals(ownerId)) {
			throw new Exception("Not allowed to update shop");
		}
		
		Shop shop = shopRepository.findById(shopId).get();
		shop.setShopName(updatedShop.getShopName());
		shop.setDescription(updatedShop.getDescription());
		shop.setContactNumber(updatedShop.getContactNumber());
		shop.updateAddress(updatedShop.getAddress());
		shop.updateHours(updatedShop.getHours());
		shop.updateShopLocation(updatedShop.getShopLocation());
		shop.setShopCategory(updatedShop.getShopCategory());
		if(shopImageData!=null) {
			shop.setImageName(shopImageData.getName());
			shop.setImageType(shopImageData.getContentType());
			shop.setShopImageData(shopImageData.getBytes());
		}
		return shopRepository.save(shop);
	}

	@Transactional
	public void deleteShopById(Integer shopId,String authHeader) throws Exception {
		Integer userId = utilityService.getUserId(authHeader);
		Integer ownerId = getOwnerId(shopId);
		String roles = utilityService.getRoles(authHeader);
		
		if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
			throw new Exception("Not allowed to delete shop.");
		}
		
		Set<User> usersToUpdate = new HashSet<>();
		
		Shop shop = shopRepository.findById(shopId).get();
		
		for(Review review:shop.getShopReviews()) {
			for(User user:review.getLikes()) {
				user.getLikedReviews().remove(review);
				usersToUpdate.add(user);
			}
			for(User user:review.getDislikes()) {
				user.getDislikedReviews().remove(review);
				usersToUpdate.add(user);
			}
		}
		
		for(Item item:shop.getItems()) {
			for(Review review:item.getItemReviews()) {
				for(User user:review.getLikes()) {
					user.getLikedReviews().remove(review);
					usersToUpdate.add(user);
				}
				for(User user:review.getDislikes()) {
					user.getDislikedReviews().remove(review);
					usersToUpdate.add(user);
				}
			}
		}
		
		userRepository.saveAll(usersToUpdate);
		shopRepository.delete(shop);;
	}
	
	public ShopDTO mapShopToShopDTO(Shop shop) {
		return new ShopDTO(shop);
	}
	
	public Page<ShopDTO> mapShopsToShopDTOs(Page<Shop> shops){
		return shops.map((Shop shop)->mapShopToShopDTO(shop));
	}
	
	public List<ShopDTO> mapShopsToShopDTOs(List<Shop> shops){
		return shops.stream().map((Shop shop)->mapShopToShopDTO(shop)).toList();
	}
	
	public Shop encodeImage(Shop shop) {
		if(shop.getShopImageData()!=null)shop.setShopImageDataB64(Base64.getEncoder().encodeToString(shop.getShopImageData())); 
		if(shop.getMarkerImageData()!=null)shop.setMarkerImageDataB64(Base64.getEncoder().encodeToString(shop.getMarkerImageData()));
		System.out.println(shop.getMarkerImageData());
		return shop;
	}
	
	public List<Shop> encodeImage(List<Shop> shops){
		for(Shop shop:shops) {
			 this.encodeImage(shop);
		}
		return shops;
	}
	
	public Page<Shop> encodeImage(Page<Shop> shops){
		for(Shop shop:shops.getContent()) {
			this.encodeImage(shop); 
		}
		return shops;
	}

	public Shop setShopMarker(Integer shopId, MultipartFile markerImageFile, String authHeader) throws Exception {
		String roles = this.utilityService.getRoles(authHeader);
		Integer userId = this.utilityService.getUserId(authHeader);
		Integer ownerId = this.getOwnerId(shopId);
		
		if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
			throw new Exception("Not allowed to set marker for this shop.");
		}
		User user = this.vendorService.getVendorById(ownerId, authHeader);
		this.subscriptionService.inValidateActiveSubscription(user);
		
		if(user.getActiveSubscription()==null) {
			throw new Exception("You are not subscribed to VENDOR+.To customize your shop markers, subsribe to VENDOR+.");
		}
		
		Shop shop = this.shopRepository.findById(shopId).get();
		shop.setMarkerImageName(markerImageFile.getName());
		shop.setMarkerImageType(markerImageFile.getContentType());
		shop.setMarkerImageData(markerImageFile.getBytes());
		
		return shopRepository.save(shop);
	}
	
	public void invalidateAndRemoveMarkers(Page<Shop> shops) {
		for(Shop shop:shops.getContent()) {
			this.subscriptionService.inValidateActiveSubscription(shop.getVendor());
			if(shop.getVendor().getActiveSubscription()==null) {
				shop.setMarkerImageData(null);
				shop.setMarkerImageDataB64(null);
				shop.setMarkerImageName(null);
			}
		}
		
	}

	@Transactional
	public ShopMarkerDTO getShopMarker(Integer shopId, String authHeader) {
		Shop shop = this.getShopById(shopId);
		ShopMarkerDTO shopMarkerDTO = new ShopMarkerDTO(shop);
		return shopMarkerDTO;
	}
	

}

package com.eaglevision.Backend.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.dto.ItemDTO;
import com.eaglevision.Backend.dto.ItemOverviewDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.EagleEye;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;
import com.eaglevision.Backend.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

	private ItemRepository itemRepository;

	private ShopService shopService;
	
	private BuyerService buyerService;
	
	private EagleEyeService eagleEyeService;
	
	private UtilityService utilityService;

	@Autowired
	public ItemService(ItemRepository itemRepository, ShopService shopService,BuyerService buyerService,EagleEyeService eagleEyeService,UtilityService utilityService) {
		this.itemRepository = itemRepository;
		this.shopService = shopService;
		this.buyerService = buyerService;
		this.eagleEyeService = eagleEyeService;
		this.utilityService = utilityService;
	}

	public Item createItem(Item item, Integer shopId,MultipartFile itemImageFile, String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Integer ownerId = shopService.getOwnerId(shopId);
		
		if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
			throw new Exception("You are not allowed to create a item.");
		}
		
		if(itemImageFile!=null) {
			item.setItemImageName(itemImageFile.getName());
			item.setItemImageType(itemImageFile.getContentType());
			item.setItemImageData(itemImageFile.getBytes());
		}
		
		Shop shop = shopService.getShopById(shopId);
		item.setShop(shop);
		return itemRepository.save(item);
	}

	@Transactional
	public Item getItem(Integer shopId, Integer itemId,Double lat,Double lon,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		Integer userId = utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			if(userId.equals(shopService.getOwnerId(shopId))) return this.encodeImage(itemRepository.findByShop_shopIdAndItemId(shopId, itemId));
			throw new Exception("Not allowed to fetch this item.");
		}
		
		Item item = itemRepository.findByshopIdAnditemId(shopId, itemId,lon,lat,radius);
		return this.encodeImage(item);
	}
	
	public Page<Item> getAllItems(Double lat,Double lon,String query,Integer pageNumber,Integer pageSize,String sortBy,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		Integer userId = utilityService.getUserId(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to fetch items.");
		}
		
		Pageable pageable;
		
		sortBy = this.utilityService.convertToActualFieldName(sortBy);
		
		if(pageSize==0) {
			pageable = Pageable.unpaged(Sort.by(sortBy));
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		}
		
		Page<Item> items = itemRepository.findAll(lon,lat,radius,query,pageable);
		
		return this.encodeImage(items);
	}
	
	public Page<ItemOverviewDTO> getAllItemOverviewDTOs(Double lat,Double lon,String query,Integer pageNumber,Integer pageSize,String sortBy,String authHeader) throws Exception {
		return this.convertToItemOverviewDTOs(this.getAllItems(lat, lon, query, pageNumber, pageSize, sortBy, authHeader));
	}
	
	
	
	public Page<ItemDTO> getAllItemDTOs(Double lat,Double lon,String query,Integer pageNumber,Integer pageSize,String sortBy,String authHeader) throws Exception {
		return this.getAllItems(lat, lon, query, pageNumber, pageSize, sortBy, authHeader).map((Item item)->new ItemDTO(item));
	}

	@Transactional
	public Page<Item> getAllItemsByShop(Integer shopId,Double lat,Double lon,String query,Integer pageNumber,Integer pageSize,String sortBy,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Double radius = utilityService.getRadius(roles);
		Integer userId = utilityService.getUserId(authHeader);
		
		Pageable pageable;
		
		if(!roles.contains("VENDOR")) sortBy = this.utilityService.convertToActualFieldName(sortBy);
		
		
		
		if(pageSize==0) {
			pageable = Pageable.unpaged(Sort.by(sortBy));
		}
		else {
			pageable = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		}
		
		if(roles.contains("VENDOR")) {
			if(userId.equals(shopService.getOwnerId(shopId))) return this.encodeImage(itemRepository.findByShop_shopIdAndShop_shopNameContainingIgnoreCase(shopId,query,pageable));
			throw new Exception("Not allowed to fetch items from this shop.");
		}
		

		
		return this.encodeImage(itemRepository.findByShopId(shopId,lon,lat,radius,query,pageable));
	}
	
	
	@Transactional
	public Page<ItemDTO> getAllItemDTOsByShop(Integer shopId,Double lat,Double lon,String query,Integer pageNumber,Integer pageSize,String sortBy,String authHeader) throws Exception {
		return getAllItemsByShop(shopId, lat, lon, query, pageNumber, pageSize, sortBy, authHeader).map((Item item)->new ItemDTO(item));
	}

	public Item updateItem(Item updatedItem,Integer shopId,Integer itemId, MultipartFile itemImageFile, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		
		Integer ownerId = shopService.getOwnerId(shopId);
		
		if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
			throw new Exception("You are not allowed to update this item.");
		}
		Item persitentItem = itemRepository.findById(itemId).get();
		persitentItem.setItemDescription(updatedItem.getItemDescription());
		persitentItem.setItemName(updatedItem.getItemName());
		persitentItem.setItemPrice(updatedItem.getItemPrice());
		
		if(itemImageFile!=null) {
			persitentItem.setItemImageName(itemImageFile.getName());
			persitentItem.setItemImageType(itemImageFile.getContentType());
			persitentItem.setItemImageData(itemImageFile.getBytes());
		}
	
		return itemRepository.save(persitentItem);
	}

	@Transactional
	public void deleteItemById(Integer shopId,Integer itemId,String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		
		
		Integer ownerId = shopService.getOwnerId(shopId);
		
		if(!roles.contains("VENDOR") || !userId.equals(ownerId)) {
			throw new Exception("You are not allowed to delete this item.");
		}
		Item item = itemRepository.findById(itemId).get();
		System.out.println(item.getItemName());
		itemRepository.delete(item);
		
		//itemRepository.deleteById(itemId);
	}

	public Item getItem(Integer shopId, Integer itemId, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		Integer userId = utilityService.getUserId(authHeader);
		Integer ownerId = shopService.getOwnerId(shopId);
		
		if(!roles.contains("VENDOR") || userId!=ownerId) {
			throw new Exception("Not allowed to fetch this item.");
		}
		
		return this.encodeImage(this.itemRepository.findByShop_shopIdAndItemId(shopId, itemId));
	}

	@Transactional
	public void setEagleEyes(Integer shopId, Integer itemId, Double lat, Double lon, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to set eagle eyes.");
		}
		
		Integer userId = utilityService.getUserId(authHeader);
		Integer eagleEyesLimit = utilityService.getEagleEyesLimit(roles);
		
		Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);
		
		if(buyer.getSetEagleEyes().size()==eagleEyesLimit) {
			throw new Exception("You have reached your Eagle eyes limit.Please unset other eagle eyes or upgrade.");
		}
		Item item = this.getItem(shopId, itemId, lat, lon, authHeader);
		EagleEye eagleEye = new EagleEye(item);
		item.getEagleEyes().add(eagleEye);
		eagleEye.setBuyer(buyer);
		
		this.itemRepository.save(item);
		return;
	}

	@Transactional
	public void removeEagleEyes(Integer shopId, Integer itemId, Double lat, Double lon, String authHeader) throws Exception {
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Not allowed to remove eagle eyes.");
		}
		
		Integer userId = utilityService.getUserId(authHeader);
		Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);
		
		Item item = this.getItem(shopId, itemId, lat, lon, authHeader);
		EagleEye eagleEye = this.eagleEyeService.getByBuyerAndItem(userId, itemId, authHeader);
		item.getEagleEyes().removeIf((EagleEye eagleEyes)->eagleEyes.getBuyer().getUserId().equals(userId));
		
		buyer.getSetEagleEyes().removeIf((EagleEye eagleEyes)->eagleEyes.getItem().getItemId().equals(itemId));
		
		this.itemRepository.save(item);
		this.buyerService.updateBuyer(buyer, userId, authHeader);
		this.eagleEyeService.removeEagleEye(eagleEye);
		
		
		return;
	}

	public List<Item> getAllItemsUnderEagleEyesOfUser(Double lat, Double lon, String authHeader) throws Exception{
		String roles = utilityService.getRoles(authHeader);
		
		if(roles.contains("VENDOR")) {
			throw new Exception("Eagle eyes are not for vendors.");
		}
		
		Integer userId = this.utilityService.getUserId(authHeader);
		
		Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);
		List<EagleEye> eagleEyes = buyer.getSetEagleEyes();
		List<Item> items = new ArrayList<Item>();
		for(EagleEye eagleEye:eagleEyes) {
			items.add(eagleEye.getItem());
		}
		return this.encodeImage(items);
		
	}
	
	public Item encodeImage(Item item) {
		if(item.getItemImageData()!=null) item.setItemImageDataB64(Base64.getEncoder().encodeToString(item.getItemImageData())); 
		return item;
	}
	
	public List<Item> encodeImage(List<Item> items) {
		for(Item item:items) {
			encodeImage(item); 
		}
		return items;
	}
	
	public Page<Item> encodeImage(Page<Item> items) {
		for(Item item:items) {
			encodeImage(item);
		}
		return items;
	}
	
	public ItemOverviewDTO convertToItemOverviewDTO(Item item){
		return new ItemOverviewDTO(item);
	}
	
	public Page<ItemOverviewDTO> convertToItemOverviewDTOs(Page<Item> items){
		return items.map((Item item)->this.convertToItemOverviewDTO(item));
	}

	@Transactional
	public ItemDTO getItemDTO(Integer shopId, Integer itemId, Double lat, Double lon, String authHeader) throws Exception {
		Item item = this.getItem(shopId, itemId, lat, lon, authHeader);
		String roles = this.utilityService.getRoles(authHeader);
		ItemDTO itemDTO;
		if(roles.contains("BUYER")) {
			Integer userId = this.utilityService.getUserId(authHeader);
			Buyer buyer = this.buyerService.getBuyerById(userId, authHeader);
			itemDTO = new ItemDTO(item,buyer);
		}
		else {
			itemDTO = new ItemDTO(item);
		}
		return itemDTO;
	}

}

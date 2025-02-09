package com.eaglevision.Backend.dto;

import java.util.Base64;

import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.Shop;

public class ItemOverviewDTO {
	
	private Integer itemId;
	
	private String itemName;
	
	private Double itemPrice;
	
	private String itemImageName;
    
    private String itemImageType;
    
    private String itemImageDataB64 ;
    
    private Integer shopId;
    
    private String shopName;
    
    private String shopImageDataB64;
	
	private String shopImageName;
	
	private String shopImageType;
	
	
	public ItemOverviewDTO() {
		
	}
	
	
	public ItemOverviewDTO(Item item) {
		this.itemId = item.getItemId();
		this.itemName = item.getItemName();
		this.itemImageName = item.getItemImageName();
		this.itemPrice = item.getItemPrice();
		this.itemImageDataB64 = this.encodeImage(item.getItemImageData());
		this.shopId = item.getShop().getShopId();
		this.shopName = item.getShop().getShopName();
		this.shopImageDataB64 = this.encodeImage(item.getShop().getShopImageData());
		this.shopImageName = item.getShop().getImageName();
		this.shopImageType = item.getShop().getImageType();
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getItemImageName() {
		return itemImageName;
	}


	public void setItemImageName(String itemImageName) {
		this.itemImageName = itemImageName;
	}


	public String getItemImageType() {
		return itemImageType;
	}


	public void setItemImageType(String itemImageType) {
		this.itemImageType = itemImageType;
	}


	public String getItemImageDataB64() {
		return itemImageDataB64;
	}


	public void setItemImageDataB64(String itemImageDataB64) {
		this.itemImageDataB64 = itemImageDataB64;
	}


	public Integer getShopId() {
		return shopId;
	}


	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getShopImageDataB64() {
		return shopImageDataB64;
	}


	public void setShopImageDataB64(String shopImageDataB64) {
		this.shopImageDataB64 = shopImageDataB64;
	}


	public String getShopImageName() {
		return shopImageName;
	}


	public void setShopImageName(String shopImageName) {
		this.shopImageName = shopImageName;
	}


	public String getShopImageType() {
		return shopImageType;
	}


	public void setShopImageType(String shopImageType) {
		this.shopImageType = shopImageType;
	}
	
	
	private String encodeImage(byte[] image) {
		return Base64.getEncoder().encodeToString(image);
	}


	public Double getItemPrice() {
		return itemPrice;
	}


	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	
}

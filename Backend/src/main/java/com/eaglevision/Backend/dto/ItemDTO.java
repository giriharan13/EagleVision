package com.eaglevision.Backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.EagleEye;
import com.eaglevision.Backend.model.Item;
import com.eaglevision.Backend.model.ItemReview;
import com.eaglevision.Backend.model.Ping;

public class ItemDTO {
    private Integer itemId;

    private String itemName, itemDescription;

    private Double itemPrice;

    private Integer vendorId;

   
    private String itemImageName;
    
    private String itemImageType;
    
    private String itemImageDataB64 ;
    
    private Boolean currentUserSetEagleEye;
   

    public ItemDTO(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.itemDescription = item.getItemDescription();
        this.itemPrice = item.getItemPrice();
        this.vendorId = item.getShop().getVendor().getUserId();
        this.itemImageName = item.getItemImageName();
        this.itemImageType = item.getItemImageType();
        this.itemImageDataB64 = item.getItemImageDataB64();
    }

    public ItemDTO(Item item, Buyer buyer) {
    	 this.itemId = item.getItemId();
         this.itemName = item.getItemName();
         this.itemDescription = item.getItemDescription();
         this.itemPrice = item.getItemPrice();
         this.vendorId = item.getShop().getVendor().getUserId();
         this.itemImageName = item.getItemImageName();
         this.itemImageType = item.getItemImageType();
         this.itemImageDataB64 = item.getItemImageDataB64();
         this.currentUserSetEagleEye = buyer.getSetEagleEyes().stream().anyMatch((EagleEye eagleEye)->eagleEye.getItem().getItemId()==this.itemId);
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
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

	public Boolean getCurrentUserSetEagleEye() {
		return currentUserSetEagleEye;
	}

	public void setCurrentUserSetEagleEye(Boolean currentUserSetEagleEye) {
		this.currentUserSetEagleEye = currentUserSetEagleEye;
	}
	
	

}

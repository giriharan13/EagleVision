package com.eaglevision.Backend.dto;

import java.util.Date;

import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.VendorResponsePing;

public class PingDTO {
	private Integer pingId;

    private Integer userId;

    private String userName;

    private Date creationDate;

    private VendorResponsePing vendorResponsePing;
    
    private Integer itemId;
    
    private Integer shopId;
    
    public PingDTO() {
    	
    }
    
    public PingDTO(BuyerCheckPing buyerCheckPing) {
    	this.pingId = buyerCheckPing.getPingId();
    	this.userId = buyerCheckPing.getBuyer().getUserId();
    	this.userName = buyerCheckPing.getBuyer().getUserName();
    	this.vendorResponsePing = buyerCheckPing.getVendorResponsePing();
    	this.itemId = buyerCheckPing.getItem().getItemId();
    	this.shopId = buyerCheckPing.getItem().getShop().getShopId();
    }

    public Integer getPingId() {
        return pingId;
    }

    public void setPingId(Integer pingId) {
        this.pingId = pingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public VendorResponsePing getVendorResponsePing() {
        return vendorResponsePing;
    }

    public void setVendorResponsePing(VendorResponsePing vendorResponsePing) {
        this.vendorResponsePing = vendorResponsePing;
    }

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
    
    
}

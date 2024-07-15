package com.eaglevision.Backend.dto;

import java.util.Date;

import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.VendorResponsePing;

public class BuyerCheckPingDTO {

    private Integer pingId;

    private Integer userId;

    private String userName;

    private Date creationDate;

    private Integer quantity;

    private VendorResponsePing vendorResponsePing;

    public BuyerCheckPingDTO() {
        super();
    }

    public BuyerCheckPingDTO(Integer userId, Date creationDate) {
        super();
        this.userId = userId;
        this.creationDate = creationDate;
        this.quantity = null;
    }

    public BuyerCheckPingDTO(Integer userId, Date creationDate, Integer quantity) {
        super();
        this.userId = userId;
        this.creationDate = creationDate;
        this.quantity = quantity;
    }

    public BuyerCheckPingDTO(BuyerCheckPing buyerCheckPing) {
        super();
        this.pingId = buyerCheckPing.getPingId();
        this.userId = buyerCheckPing.getBuyer().getUserId();
        this.userName = buyerCheckPing.getBuyer().getUserName();
        this.creationDate = buyerCheckPing.getCreationDate();
        this.vendorResponsePing = buyerCheckPing.getVendorResponsePing();
        this.quantity = null;
    }

    public BuyerCheckPingDTO(VendorResponsePing vendorResponsePing) {
        super();
        this.pingId = vendorResponsePing.getPingId();
        this.userId = vendorResponsePing.getVendor().getUserId();
        this.userName = vendorResponsePing.getVendor().getUserName();
        this.creationDate = vendorResponsePing.getCreationDate();
        this.quantity = vendorResponsePing.getQuantity();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

}

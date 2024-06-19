package com.eaglevision.Backend.dto;

import java.util.Date;

import com.eaglevision.Backend.model.BuyerCheckPing;
import com.eaglevision.Backend.model.VendorResponsePing;

public class PingResponseDTO {
    private Integer type;

    private Integer pingId;

    private Integer userId;

    private String userName;

    private Date creationDate;

    private Integer quantity;

    public PingResponseDTO() {
        super();
    }

    public PingResponseDTO(Integer userId, Date creationDate) {
        super();
        this.type = 0;
        this.userId = userId;
        this.creationDate = creationDate;
        this.quantity = null;
    }

    public PingResponseDTO(Integer userId, Date creationDate, Integer quantity) {
        super();
        this.type = 1;
        this.userId = userId;
        this.creationDate = creationDate;
        this.quantity = quantity;
    }

    public PingResponseDTO(BuyerCheckPing buyerCheckPing, String userName) {
        super();
        this.type = 0;
        this.pingId = buyerCheckPing.getPingId();
        this.userId = buyerCheckPing.getBuyer().getUserId();
        this.userName = userName;
        this.creationDate = buyerCheckPing.getCreationDate();
        this.quantity = null;
    }

    public PingResponseDTO(VendorResponsePing vendorResponsePing, String userName) {
        super();
        this.type = 1;
        this.pingId = vendorResponsePing.getPingId();
        this.userId = vendorResponsePing.getVendor().getUserId();
        this.userName = userName;
        this.creationDate = vendorResponsePing.getCreationDate();
        this.quantity = vendorResponsePing.getQuantity();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}

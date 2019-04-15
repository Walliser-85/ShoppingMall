package com.example.simploncenter.db.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;


public class ShopEntity {

    private int idShop;
    private String shopName;
    private String description;
    private byte[] picture;

    public ShopEntity(){ }

    public ShopEntity(String shopName, String description, byte[] picture){
        this.shopName = shopName;
        this.description = description;
        this.picture = picture;
    }

    @Exclude
    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("shop name", shopName);
        result.put("description", description);
        result.put("picture", picture);

        return result;
    }
}

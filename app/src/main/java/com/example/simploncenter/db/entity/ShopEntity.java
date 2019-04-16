package com.example.simploncenter.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ShopEntity {
    private String idShop;

    private String shopName;

    private String description;

    private byte[] picture;

    public ShopEntity(String shopName, String description){
        this.shopName = shopName;
        this.description = description;
    }

    @Exclude
    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
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

    @Exclude
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("shopName", shopName);
        result.put("description", description);
        return result;
    }
}

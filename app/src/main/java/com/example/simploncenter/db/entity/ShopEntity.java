package com.example.simploncenter.db.entity;

import android.arch.persistence.room.Ignore;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ShopEntity {

    private String idShop;

    private String shopName;

    private String description;

    private Map<String, Boolean> articles = new HashMap<>();

    private byte[] picture;

    @Ignore
    public ShopEntity(){ }

    public ShopEntity(String shopName, String description){
        this.shopName = shopName;
        this.description = description;
    }

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Map<String, Boolean> getArticles() {
        return articles;
    }

    public void setArticles(Map<String, Boolean> articles) {
        this.articles = articles;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("shopName", shopName);
        result.put("description", description);
        result.put("articles", articles);
        return result;
    }
}

package com.example.simploncenter.db.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ArticleEntity {

    private String idArticle;
    private String toShop;
    private String articleName;
    private String description;
    private String shortDescription;
    private float price;
    private byte[] picture;


    public ArticleEntity(){ }

    public ArticleEntity(String toShop, String articleName, String description, String shortDescription, float price) {
        this.toShop = toShop;
        this.articleName = articleName;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price;
    }

    @Exclude
    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Exclude
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getToShop() {
        return toShop;
    }

    public void setToShop(String toShop) {
        this.toShop = toShop;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("toShop", toShop);
        result.put("articleName", articleName);
        result.put("description", description);
        result.put("shortDescription", shortDescription);
        result.put("price", price);

        return result;
    }

}

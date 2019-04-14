package com.example.simploncenter.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticleEntity {

    @PrimaryKey(autoGenerate = true)
    private int idArticle;

    @ForeignKey(entity=ShopEntity.class,  parentColumns = "idShop", childColumns = "toShop")
    private String toShop;

    @ColumnInfo(name="article_name")
    private String articleName;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(name="short_description")
    private String shortDescription;

    @ColumnInfo(name="price")
    private float price;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] picture;

    @Ignore
    public ArticleEntity(){ }

    public ArticleEntity(String articleName,String toShop, String description, String shortDescription, float price, byte[] picture) {
        this.articleName = articleName;
        this.description = description;
        this.shortDescription = shortDescription;
        this.picture = picture;
        this.price=price;
        this.toShop=toShop;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
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
}

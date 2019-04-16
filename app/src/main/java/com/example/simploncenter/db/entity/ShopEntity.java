package com.example.simploncenter.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity(tableName = "shops")
public class ShopEntity {
    @PrimaryKey(autoGenerate = true)
    private int idShop;

    @ColumnInfo(name="shop_name")
    private String shopName;

    @ColumnInfo(name="description")
    private String description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] picture;

    @Ignore
    public ShopEntity(){ }

    public ShopEntity(String shopName, String description, byte[] picture){
        this.shopName = shopName;
        this.description = description;
        this.picture = picture;
    }

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
}

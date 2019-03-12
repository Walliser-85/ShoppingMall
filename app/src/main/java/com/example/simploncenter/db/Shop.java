package com.example.simploncenter.db;

import android.arch.persistence.room.*;
import android.graphics.Bitmap;

@Entity(tableName ="shops", indices = {@Index(value = {"idShop"}, unique = true)})
public class Shop {
    @PrimaryKey(autoGenerate = true)
    private int idShop;

    @ColumnInfo(name="shop_name")
    private String shopName;

    @ColumnInfo(name="short_description")
    private String shortDescription;

    @ColumnInfo(name="long_description")
    private String longDescription;

    @Ignore
    Bitmap picture;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}

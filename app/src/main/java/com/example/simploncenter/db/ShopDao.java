package com.example.simploncenter.db;

import android.arch.persistence.room.*;

public interface ShopDao {

    @Insert
    public void addShop(Shop shop);
}

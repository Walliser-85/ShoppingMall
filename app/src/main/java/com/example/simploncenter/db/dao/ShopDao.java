package com.example.simploncenter.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.example.simploncenter.db.entity.ShopEntity;

import java.sql.SQLClientInfoException;
import java.util.List;

@Dao
public interface ShopDao {

    @Query("SELECT * FROM shops WHERE idShop = :id LIMIT 1")
    ShopEntity getByIdCurrent(int id);

    @Query("SELECT * FROM shops WHERE idShop = :id")
    LiveData<ShopEntity> getById(int id);

    @Query("SELECT idShop FROM shops WHERE shop_name = :name")
    int getId(String name);

    @Query("SELECT * FROM shops")
    LiveData<List<ShopEntity>> getAllShops();

    @Query("SELECT shop_name FROM shops")
    LiveData<List<String>> getAllShopNames();

    @Insert
    long insert(ShopEntity shop) throws SQLClientInfoException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ShopEntity> shops);

    @Update
    void update(ShopEntity shop);

    @Delete
    void delete(ShopEntity shop);

    @Query("DELETE FROM shops")
    void deleteAll();
}

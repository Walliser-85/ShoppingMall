package com.example.simploncenter.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;

import java.sql.SQLClientInfoException;
import java.util.List;

public interface ArticleDao {
    @Query("Select * FROM Articles")
    public List<ArticleEntity> getAll();

    @Insert
    long insert(ArticleEntity article) throws SQLClientInfoException;

    @Query("SELECT * FROM Articles WHERE aid IN (:aid)")
    List<ArticleEntity> loadAllByIds(int[] articleIds);

    @Query("SELECT * FROM Articles WHERE Name LIKE :first LIMIT 1")
    ArticleEntity findByName(String first);

    @Insert
    void insertAll(ArticleEntity article);

    @Delete
    void delete(ArticleEntity article);

    @Query("DELETE FROM Articles")
    void deleteAll();
}

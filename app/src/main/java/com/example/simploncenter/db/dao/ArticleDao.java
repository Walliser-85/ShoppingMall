package com.example.simploncenter.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import com.example.simploncenter.db.entity.ArticleEntity;

import java.sql.SQLClientInfoException;
import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles WHERE idArticle = :id")
    LiveData<ArticleEntity> getById(int id);

    @Query("SELECT * FROM articles WHERE toShop = :toShop")
    LiveData<List<ArticleEntity>> getByShopId(String toShop);

    @Query("SELECT * FROM articles")
    LiveData<List<ArticleEntity>> getAllArticles();

    @Insert
    long insert(ArticleEntity article) throws SQLClientInfoException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArticleEntity> articles);

    @Update
    void update(ArticleEntity article);

    @Delete
    void delete(ArticleEntity article);

    @Query("DELETE FROM articles")
    void deleteAll();

}

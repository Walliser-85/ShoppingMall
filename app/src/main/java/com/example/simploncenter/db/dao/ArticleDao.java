package com.example.simploncenter.db.dao;

import android.arch.persistence.room.Dao;

@Dao
public interface ArticleDao {
    /*@Query("SELECT * FROM articles WHERE idArticle = :id")
    LiveData<ArticleEntity> getById(int id);

    @Query("SELECT * FROM articles")
    LiveData<ArticleEntity> getAllArticles();

    @Insert
    long insert(ArticleEntity article) throws SQLClientInfoException;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ArticleEntity> articles);

    @Update
    void update(ArticleEntity article);

    @Delete
    void delete(ArticleEntity article);

    @Query("DELETE FROM articles")
    void deleteAll();*/
}

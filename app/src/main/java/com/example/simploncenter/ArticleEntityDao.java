package com.example.simploncenter;

import java.util.List;

@Dao
public interface ArticleEntityDao {
    @Query("Select * FROM Articles")
    public List<ArticleEntity> getAll();

    @Query("SELECT * FROM Articles WHERE aid IN (:aid)")
    List<ArticleEntity> loadAllByIds(int[] articleIds);

    @Query("SELECT * FROM Articles WHERE Name LIKE :first LIMIT 1")
    ArticleEntity findByName(String first);

    @Insert
    void insertAll(ArticleEntity article);

    @Delete
    void delete(ArticleEntity article);
}

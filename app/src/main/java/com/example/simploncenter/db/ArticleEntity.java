package com.example.simploncenter.db;

import android.arch.persistence.room.*;

@Entity(tableName = "Articles")
public class ArticleEntity {
    @PrimaryKey(autoGenerate = true)
    private int aid;
    @ColumnInfo(name ="Name")
    private String nameArticle;
    @ColumnInfo(name ="Description")
    private String description;
    @ColumnInfo(name ="ID shop")
    private int idShop;
    @ColumnInfo(name ="Price")
    private float priceArticle;
    @ColumnInfo(name ="Picture")
    private String picture;
}

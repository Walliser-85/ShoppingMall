package com.example.simploncenter;
@Database(entities = {ArticleEntity.class}, version=1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ArticleEntityDao articleDao();
}

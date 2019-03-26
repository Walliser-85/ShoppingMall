package com.example.simploncenter.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.async.article.CreateArticle;
import com.example.simploncenter.db.async.article.DeleteArticle;
import com.example.simploncenter.db.async.article.UpdateArticle;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

import java.util.List;

public class ArticleRepository {
    private static ArticleRepository instance;

    private ArticleRepository() {

    }

    public static ArticleRepository getInstance() {
        if (instance == null) {
            synchronized (ArticleRepository.class) {
                if (instance == null) {
                    instance = new ArticleRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ArticleEntity> getArticle(final int clientId, Application application) {
        return ((BaseApp) application).getDatabase().articleDao().getById(clientId);
    }

    public LiveData<List<ArticleEntity>> getAllArticle(Application application) {
        return ((BaseApp) application).getDatabase().articleDao().getAllArticles();
    }

    public void insert(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new CreateArticle(application, callback).execute(article);
    }

    public void update(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new UpdateArticle(application, callback).execute(article);
    }

    public void delete(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new DeleteArticle(application, callback).execute(article);
    }


}

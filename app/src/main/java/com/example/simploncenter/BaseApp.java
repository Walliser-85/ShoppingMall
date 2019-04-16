package com.example.simploncenter;

import android.app.Application;

import com.example.simploncenter.db.repository.ArticleRepository;
import com.example.simploncenter.db.repository.ShopRepository;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ShopRepository getShopRepository() {
        return ShopRepository.getInstance();
    }

    public ArticleRepository getArticleRepository() {
        return ArticleRepository.getInstance();
    }
}

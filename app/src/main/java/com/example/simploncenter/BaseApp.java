package com.example.simploncenter;

import android.app.Application;

import com.example.simploncenter.db.AppDatabase;
import com.example.simploncenter.db.repository.ArticleRepository;
import com.example.simploncenter.db.repository.ShopRepository;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ShopRepository getAccountRepository() {
        return ShopRepository.getInstance();
    }

    public ArticleRepository getClientRepository() {
        return ArticleRepository.getInstance();
    }
}

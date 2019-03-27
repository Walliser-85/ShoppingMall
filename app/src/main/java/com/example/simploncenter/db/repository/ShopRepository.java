package com.example.simploncenter.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.async.shop.CreateShop;
import com.example.simploncenter.db.async.shop.DeleteShop;
import com.example.simploncenter.db.async.shop.UpdateShop;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

import java.util.List;

public class ShopRepository {
    private static ShopRepository instance;

    private ShopRepository() {
    }

    public static ShopRepository getInstance() {
        if (instance == null) {
            synchronized (ShopRepository.class) {
                if (instance == null) {
                    instance = new ShopRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ShopEntity> getShop(final int clientId, Application application) {
        return ((BaseApp) application).getDatabase().shopDao().getById(clientId);
    }

    public LiveData<List<ShopEntity>> getAllShops(Application application) {
        return ((BaseApp) application).getDatabase().shopDao().getAllShops();
    }

    public LiveData<List<String>> getAllShopNames(Application application) {
        return ((BaseApp) application).getDatabase().shopDao().getAllShopNames();
    }

    public void insert(final ShopEntity shop, OnAsyncEventListener callback,
                       Application application) {
        new CreateShop(application, callback).execute(shop);
    }

    public void update(final ShopEntity shop, OnAsyncEventListener callback,
                       Application application) {
        new UpdateShop(application, callback).execute(shop);
    }

    public void delete(final ShopEntity shop, OnAsyncEventListener callback,
                       Application application) {
        new DeleteShop(application, callback).execute(shop);
    }
}

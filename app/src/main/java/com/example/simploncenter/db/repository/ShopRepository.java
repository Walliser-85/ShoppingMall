package com.example.simploncenter.db.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.firebase.ShopListLiveData;
import com.example.simploncenter.db.firebase.ShopLiveData;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<ShopEntity> getShop(final String clientId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shops").child(clientId);
        return new ShopLiveData(reference);
    }

    public LiveData<List<ShopEntity>> getAllShops() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shops");
        return new ShopListLiveData(reference);
    }

    /*
    public LiveData<List<String>> getAllShopNames() {
        return ((BaseApp) application).getDatabase().shopDao().getAllShopNames();
    }

    public int  getShopId(final String name) {
        return ((BaseApp) application).getDatabase().shopDao().getId(name);
    }*/

    public void insert(final ShopEntity shop, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("shops").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(id)
                .setValue(shop, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ShopEntity shop, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(shop.getIdShop())
                .updateChildren(shop.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ShopEntity shop, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(shop.getIdShop())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}

package com.example.simploncenter.viewmodel.shop;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.repository.ShopRepository;
import com.example.simploncenter.util.OnAsyncEventListener;

import java.util.List;

public class ShopViewModel extends AndroidViewModel {
    private Application application;
    private ShopRepository repository;
    private final MediatorLiveData<ShopEntity> observableShop;

    public ShopViewModel(@NonNull Application application,
                         final String shopId, ShopRepository repository) {
        super(application);

        this.application = application;
        this.repository = repository;

        observableShop = new MediatorLiveData<>();
        observableShop.setValue(null);

        LiveData<ShopEntity> shop = repository.getShop(shopId);

        observableShop.addSource(shop, observableShop::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String shopId;
        private final ShopRepository repository;

        public Factory(@NonNull Application application, String shopId) {
            this.application = application;
            this.shopId = shopId;
            repository = ((BaseApp) application).getShopRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShopViewModel(application, shopId, repository);
        }
    }

    public LiveData<ShopEntity> getShop() {
        return observableShop;
    }

    public void createShop(ShopEntity shop, OnAsyncEventListener callback,byte[] data) {
        repository.insert(shop, callback, data);
    }

    public void updateShop(ShopEntity shop, OnAsyncEventListener callback, byte[] data) {
        repository.update(shop, callback, data);
    }

    public void deleteClient(ShopEntity shop, OnAsyncEventListener callback){
        repository.delete(shop,callback);
    }
}

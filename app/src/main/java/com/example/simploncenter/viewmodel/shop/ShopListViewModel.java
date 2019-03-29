package com.example.simploncenter.viewmodel.shop;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.repository.ShopRepository;

import java.util.List;

public class ShopListViewModel extends AndroidViewModel {
    private ShopRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ShopEntity>> observableShops;
    private final MediatorLiveData<List<String>> observableShopNames;

    public ShopListViewModel(@NonNull Application application,
                             ShopRepository repository) {
        super(application);

        this.repository = repository;
        this.application = application;

        observableShops = new MediatorLiveData<>();
        observableShopNames= new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableShops.setValue(null);
        observableShopNames.setValue(null);

        LiveData<List<ShopEntity>> shops = repository.getAllShops(application);
        LiveData<List<String>> names = repository.getAllShopNames(application);

        // observe the changes of the entities from the database and forward them
        observableShops.addSource(shops, observableShops::setValue);
        observableShopNames.addSource(names,observableShopNames::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ShopRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ShopRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShopListViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<ShopEntity>> getShops() {
        return observableShops;
    }

    public LiveData<List<String>> getShopNames() {
        return observableShopNames;
    }
}

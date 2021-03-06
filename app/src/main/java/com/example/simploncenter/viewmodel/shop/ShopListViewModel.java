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
    private ShopEntity shop;
    String idShop;

    public ShopListViewModel(@NonNull Application application,
                             ShopRepository repository) {
        super(application);

        this.repository = repository;
        this.application = application;

        observableShops = new MediatorLiveData<>();


        // set by default null, until we get data from the database.
        observableShops.setValue(null);

        LiveData<List<ShopEntity>> shops = repository.getAllShops();
        //LiveData<List<String>> names = repository.getAllShopNames(application);

        // observe the changes of the entities from the database and forward them
        observableShops.addSource(shops, observableShops::setValue);
        //observableShopNames.addSource(names,observableShopNames::setValue);
    }

    public ShopListViewModel(@NonNull Application application,
                             ShopRepository repository, String id) {
        super(application);

        this.repository = repository;
        this.application = application;
        this.idShop=id;

        observableShops = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableShops.setValue(null);

        LiveData<List<ShopEntity>> shops = repository.getAllShops();
        //LiveData<List<String>> names = repository.getAllShopNames(application);
        //shop= repository.getShopCurrent(id,application);

        // observe the changes of the entities from the database and forward them
        observableShops.addSource(shops, observableShops::setValue);
        //observableShopNames.addSource(names,observableShopNames::setValue);

    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ShopRepository repository;

        private String id ="";


        public Factory(@NonNull Application application, String id) {
            this.application = application;
            repository = ShopRepository.getInstance();
            this.id=id;
        }
        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ShopRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            if (id.equals("")){
                return (T) new ShopListViewModel(application, repository);
            } else {
                return (T) new ShopListViewModel(application, repository, id);
            }

        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<ShopEntity>> getShops() {
        return observableShops;
    }

    public ShopEntity getShop(){
        return shop;
    }

}

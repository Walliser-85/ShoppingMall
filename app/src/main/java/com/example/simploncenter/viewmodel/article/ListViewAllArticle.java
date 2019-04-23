package com.example.simploncenter.viewmodel.article;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.repository.ArticleRepository;

import java.util.List;

public class ListViewAllArticle extends AndroidViewModel {

    private ArticleRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<ArticleEntity>> observableArticle;
    private final MediatorLiveData<List<ArticleEntity>> observableArticleByShop;

    public ListViewAllArticle(@NonNull Application application, String shopId, ArticleRepository repository) {

        super(application);

        this.repository = repository;

        observableArticle = new MediatorLiveData<>();
        observableArticleByShop = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableArticle.setValue(null);

        LiveData<List<ArticleEntity>> article = repository.getAllArticle();
        LiveData<List<ArticleEntity>> articleByShop = repository.getByShop(shopId);

        // observe the changes of the entities from the database and forward them
        observableArticle.addSource(article, observableArticle::setValue);
        observableArticleByShop.addSource(articleByShop, observableArticleByShop::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ArticleRepository repository;

        private final String shopId;

        public Factory(@NonNull Application application, String shopId) {
            this.application = application;
            repository = ArticleRepository.getInstance();
            this.shopId = shopId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ListViewAllArticle(application, shopId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<ArticleEntity>> getArticles() {
        return observableArticle;
    }
    //einen Artikel
    public LiveData<ArticleEntity> getArticle(String ID) {
        return repository.getArticle(ID);
    }
    public LiveData<List<ArticleEntity>> getArticlesByShop() { return observableArticleByShop; }
}

package com.example.simploncenter.viewmodel.article;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.repository.ArticleRepository;
import com.example.simploncenter.util.OnAsyncEventListener;

public class ArticleViewModel extends AndroidViewModel {
        private Application application;
        private ArticleRepository repository;
        private final MediatorLiveData<ArticleEntity> observableArticle;

        public ArticleViewModel(@NonNull Application application,
                             final int articleId, ArticleRepository repository) {
            super(application);

            this.application = application;
            this.repository = repository;

            observableArticle = new MediatorLiveData<>();
            observableArticle.setValue(null);

            LiveData<ArticleEntity> article = repository.getArticle(articleId, application);

            observableArticle.addSource(article, observableArticle::setValue);
        }

        public static class Factory extends ViewModelProvider.NewInstanceFactory {

            @NonNull
            private final Application application;
            private final int shopId;
            private final ArticleRepository repository;

            public Factory(@NonNull Application application, int shopId) {
                this.application = application;
                this.shopId = shopId;
                repository = ((BaseApp) application).getClientRepository();
            }

            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                //noinspection unchecked
                return (T) new ArticleViewModel(application, shopId, repository);
            }
        }

        public LiveData<ArticleEntity> getArticle() {
            return observableArticle;
        }

        public void createShop(ArticleEntity article, OnAsyncEventListener callback) {
            repository.insert(article, callback, application);
        }

        public void updateShop(ArticleEntity article, OnAsyncEventListener callback) {
            repository.update(article, callback, application);
        }

        public void deleteClient(ArticleEntity article, OnAsyncEventListener callback) {
            repository.delete(article, callback, application);
        }

}
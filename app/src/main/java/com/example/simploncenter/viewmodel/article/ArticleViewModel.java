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
                             final String articleId, ArticleRepository repository) {
            super(application);

            this.application = application;
            this.repository = repository;

            observableArticle = new MediatorLiveData<>();
            observableArticle.setValue(null);

            LiveData<ArticleEntity> article = repository.getArticle(articleId);

            observableArticle.addSource(article, observableArticle::setValue);
        }

        public static class Factory extends ViewModelProvider.NewInstanceFactory {

            @NonNull
            private final Application application;
            private final String articleID;
            private final ArticleRepository repository;

            public Factory(@NonNull Application application, String articleID) {
                this.application = application;
                this.articleID = articleID;
                repository = ((BaseApp) application).getArticleRepository();
            }

            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                //noinspection unchecked
                return (T) new ArticleViewModel(application, articleID, repository);
            }
        }

        public LiveData<ArticleEntity> getArticle() {
            return observableArticle;
        }

        public void createArticle(ArticleEntity article, OnAsyncEventListener callback, byte[] data) {
            repository.insert(article, callback, data);
        }

        public void updateArticle(ArticleEntity article, OnAsyncEventListener callback, byte[] data) {
            repository.update(article, callback,data);
        }

        public void deleteArticle(ArticleEntity article, OnAsyncEventListener callback) {
            repository.delete(article, callback);
        }

}

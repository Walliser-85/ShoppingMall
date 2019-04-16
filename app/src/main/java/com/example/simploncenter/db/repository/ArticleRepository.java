package com.example.simploncenter.db.repository;

import android.arch.lifecycle.LiveData;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.firebase.ArticleListLiveData;
import com.example.simploncenter.db.firebase.ArticleLiveData;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ArticleRepository {
    private static ArticleRepository instance;

    public static ArticleRepository getInstance() {
        if (instance == null) {
            synchronized (ArticleRepository.class) {
                if (instance == null) {
                    instance = new ArticleRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ArticleEntity> getArticle(final String articleId) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("articles")
                .child(articleId);
        return new ArticleLiveData(reference);
    }

    public LiveData<List<ArticleEntity>> getAllArticle() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("articles")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return new ArticleListLiveData(reference);
    }

    public LiveData<List<ArticleEntity>> getByShop(final String idShop) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(idShop)
                .child("articles");
        return new ArticleListLiveData(reference);
    }

    public void insert(final ArticleEntity article, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(article.getToShop())
                .child("articles");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(article.getToShop())
                .child("articles")
                .child(key)
                .setValue(article, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ArticleEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(account.getToShop())
                .child("articles")
                .child(account.getIdArticle())
                .updateChildren(account.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ArticleEntity account, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(account.getToShop())
                .child("articles")
                .child(account.getIdArticle())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}






    /*
    public LiveData<List<ArticleEntity>> getAllArticle(Application application) {
        return ((BaseApp) application).getDatabase().articleDao().getAllArticles();
    }

    public LiveData<List<ArticleEntity>> getByShopName(final String shopId, Application application){
        return ((BaseApp) application).getDatabase().articleDao().getByShopId(shopId);
    }

    public void insert(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new CreateArticle(application, callback).execute(article);
    }

    public void update(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new UpdateArticle(application, callback).execute(article);
    }

    public void delete(final ArticleEntity article, OnAsyncEventListener callback,
                       Application application) {
        new DeleteArticle(application, callback).execute(article);
    }


}*/

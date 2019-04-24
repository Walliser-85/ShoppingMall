package com.example.simploncenter.db.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.firebase.ArticleListLiveData;
import com.example.simploncenter.db.firebase.ArticleLiveData;
import com.example.simploncenter.db.firebase.ShopLiveData;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class ArticleRepository {
    private static ArticleRepository instance;

    private ArticleRepository(){
    }

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("articles").child(articleId);
        return new ArticleLiveData(reference);
    }

    public LiveData<List<ArticleEntity>> getAllArticle() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("articles");
        return new ArticleListLiveData(reference);
    }

    public LiveData<List<ArticleEntity>> getByShop(final String shopId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shops").child(shopId+"/articles");
        ShopLiveData shop = new ShopLiveData(reference);
        ShopEntity test = shop.getValue();

        reference = FirebaseDatabase.getInstance().getReference("articles");
        return new ArticleListLiveData(reference);
    }

    public void insert(final ArticleEntity article, final OnAsyncEventListener callback, byte [] data) {

        String key = FirebaseDatabase.getInstance().getReference("articles").push().getKey();
        // add an article
        FirebaseDatabase.getInstance()
                .getReference("articles")
                .child(key)
                .setValue(article.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("articles/"+key+".png");

                        UploadTask uploadTask = pathReference.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                callback.onFailure(exception);
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                callback.onSuccess();
                            }
                        });
                    }
                });
        //set Article in the Shop
        FirebaseDatabase.getInstance()
                .getReference("shops/"+article.getToShop()+"/articles")
                .child(key)
                .setValue(true);

    }

    public void update(final ArticleEntity article, OnAsyncEventListener callback, byte [] data) {
        FirebaseDatabase.getInstance()
                .getReference("articles")
                .child(article.getIdArticle())
                .updateChildren(article.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("articles/"+article.getIdArticle()+".png");

                        UploadTask uploadTask = pathReference.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                callback.onFailure(exception);
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                callback.onSuccess();
                            }
                        });
                    }
                });
    }

    public void delete(final ArticleEntity article, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("articles")
                .child(article.getIdArticle())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        //Delete in Shop
        FirebaseDatabase.getInstance()
                .getReference("shops/"+article.getToShop()+"/articles")
                .child(article.getIdArticle())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        //Delete picture
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child("articles/"+article.getIdArticle()+".png");

        pathReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onFailure(exception);
            }
        });
    }

}

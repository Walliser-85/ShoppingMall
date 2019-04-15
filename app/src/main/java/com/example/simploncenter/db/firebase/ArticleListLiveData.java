package com.example.simploncenter.db.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleListLiveData extends LiveData<List<ArticleEntity>> {
    private static final String TAG = "ArticleListLiveData";

    private final DatabaseReference reference;
    private final String shop;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ArticleListLiveData(DatabaseReference ref, String shop) {
        reference = ref;
        this.shop = shop;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toArticles(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ArticleEntity> toArticles(DataSnapshot snapshot) {
        List<ArticleEntity> articles = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ArticleEntity entity = childSnapshot.getValue(ArticleEntity.class);
            entity.setIdArticle(childSnapshot.getKey());
            entity.setToShop(shop);
            articles.add(entity);
        }
        return articles;
    }
}
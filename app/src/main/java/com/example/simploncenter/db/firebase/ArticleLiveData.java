package com.example.simploncenter.db.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ArticleLiveData extends LiveData<ArticleEntity> {
    private static final String TAG = "ArticleLiveData";

    private final DatabaseReference reference;
    private final String article;
    private final ArticleLiveData.MyValueEventListener listener = new ArticleLiveData.MyValueEventListener();

    public ArticleLiveData(DatabaseReference ref) {
        reference = ref;
        article = ref.getParent().getParent().getKey();
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
            ArticleEntity entity = dataSnapshot.getValue(ArticleEntity.class);
            entity.setIdArticle(dataSnapshot.getKey());
            entity.setArticleName(article);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }
}
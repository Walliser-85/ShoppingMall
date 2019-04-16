package com.example.simploncenter.db.firebase;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.simploncenter.db.entity.ShopEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopListLiveData extends LiveData<List<ShopEntity>> {
    private static final String TAG = "ShopListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ShopListLiveData(DatabaseReference ref) {
        reference = ref;
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
            setValue(toShops(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ShopEntity> toShops(DataSnapshot snapshot) {
        List<ShopEntity> shops = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ShopEntity entity = childSnapshot.getValue(ShopEntity.class);
            entity.setIdShop(childSnapshot.getKey());
            shops.add(entity);
        }
        return shops;
    }
}

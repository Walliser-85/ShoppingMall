package com.example.simploncenter.db.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.firebase.ShopListLiveData;
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

public class ShopRepository {
    private static ShopRepository instance;

    private ShopRepository() {
    }

    public static ShopRepository getInstance() {
        if (instance == null) {
            synchronized (ShopRepository.class) {
                if (instance == null) {
                    instance = new ShopRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<ShopEntity> getShop(final String clientId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shops").child(clientId);
        return new ShopLiveData(reference);
    }

    public LiveData<List<ShopEntity>> getAllShops() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shops");
        return new ShopListLiveData(reference);
    }

    public void insert(final ShopEntity shop, final OnAsyncEventListener callback, byte[] data) {
        String id = FirebaseDatabase.getInstance().getReference("shops").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(id)
                .setValue(shop, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("shops/"+id+".png");

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

    public void update(final ShopEntity shop, final OnAsyncEventListener callback, byte[] data) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(shop.getIdShop())
                .updateChildren(shop.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("shops/"+shop.getIdShop()+".png");

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

    public void delete(final ShopEntity shop, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("shops")
                .child(shop.getIdShop())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        //delete picture
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference pathReference = storageRef.child("shops/"+shop.getIdShop()+".png");
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
                });

    }
}

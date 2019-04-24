package com.example.simploncenter.Adapter;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {
    private List<ShopEntity> shopList = new ArrayList<>();
    private final OnItemClickListener listener;

    public ShopAdapter(List<ShopEntity> shopList,OnItemClickListener listener) {
        this.shopList = shopList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listview_layout, viewGroup, false);
        return new ShopHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder shopHolder, int i) {
        ShopEntity currentShop = shopList.get(i);
        shopHolder.textViewShopName.setText(currentShop.getShopName());
        shopHolder.textViewShopDescription.setText(currentShop.getDescription());
        shopHolder.bind(shopList.get(i), listener);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child("shops/"+shopList.get(i).getIdShop()+".png");

        final long ONE_MEGABYTE = 1024 * 1024 *5;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                shopHolder.ivw.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                System.out.print("error");
                String id = shopList.get(i).getIdShop();
                System.out.print(id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public void setShop(List<ShopEntity> shop) {
        this.shopList = shop;
        notifyDataSetChanged();
    }

    public ShopEntity getShopAt(int position) {
        return shopList.get(position);
    }

    class ShopHolder extends RecyclerView.ViewHolder{
        private TextView textViewShopName;
        private TextView textViewShopDescription;
        private ImageView ivw;


        public ShopHolder(@NonNull View itemView) {
            super(itemView);
            textViewShopName = itemView.findViewById(R.id.tvshopname);
            textViewShopDescription = itemView.findViewById(R.id.tvdescription);
            ivw = itemView.findViewById(R.id.imageView);
        }

        public void bind(final ShopEntity shop, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(shop);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ShopEntity item);

    }
}

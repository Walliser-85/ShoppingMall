package com.example.simploncenter.Adapter;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopHolder> {
    private List<ShopEntity> shopList = new ArrayList<>();
    Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};
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
        //shopHolder.ivw.setImageBitmap(BitmapFactory.decodeByteArray(shopList.get(i).getPicture(), 0, shopList.get(i).getPicture().length));
        shopHolder.ivw.setImageBitmap(BitmapFactory.decodeByteArray(shopList.get(i).getPicture(), 0, shopList.get(i).getPicture().length));
        shopHolder.bind(shopList.get(i), listener);
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

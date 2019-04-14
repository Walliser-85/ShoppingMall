package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simploncenter.Adapter.CustomListView;
import com.example.simploncenter.Adapter.ShopAdapter;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.repository.ShopRepository;
import com.example.simploncenter.viewmodel.shop.ShopListViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllShops extends Fragment {
    private ListView listview;
    private List<ShopEntity> shopList;
    private ShopListViewModel viewModel;

    ListView lst;
    private ShopRepository repository;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment

            RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_shop);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            shopList = new ArrayList<ShopEntity>();

            final ShopAdapter adapter = new ShopAdapter(shopList, new ShopAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ShopEntity item) {
                    Intent intent = new Intent(getActivity(), CurrentShop.class);
                    intent.putExtra("shopId", item.getIdShop());
                    intent.putExtra("shopName", item.getShopName());
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(adapter);

            ShopListViewModel.Factory factory = new ShopListViewModel.Factory(getActivity().getApplication());
            viewModel = ViewModelProviders.of(this, factory).get(ShopListViewModel.class);
            viewModel.getShops().observe(this, shopEntities -> {
                if (shopEntities != null) {
                    adapter.setShop(shopEntities);
                }
            });
        return rootView;
    }
}

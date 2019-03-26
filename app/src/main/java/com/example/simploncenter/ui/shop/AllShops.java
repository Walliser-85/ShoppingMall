package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.simploncenter.Adapter.CustomListView;
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
    String[] shopname;
    String[] desc ={"This is Migros", "This is C&A", "This is H&M", "This is Interdiscount"};
    Integer[] articles = {10, 20, 30, 40};
    Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment

            listview = rootView.findViewById(R.id.lwShops);
            shopList = new ArrayList<>();

            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplication(), android.R.layout.simple_list_item_1);
            //CustomListView adapter = new CustomListView(getActivity(), shopList);
            listview.setAdapter(adapter);
            ShopListViewModel.Factory factory = new ShopListViewModel.Factory(getActivity().getApplication());
            viewModel = ViewModelProviders.of(this, factory).get(ShopListViewModel.class);
            viewModel.getShops().observe(this, shopEntities -> {
                if (shopEntities != null) {
                    shopList = shopEntities;
                    adapter.clear();
                    adapter.addAll(shopList);
                    //adapter.updateData(shopEntities);
                    //listview.setAdapter(adapter);
                    //shopList = shopEntities;
                }
            });

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), CurrentShop.class);
                    intent.putExtra("shopId", shopList.get(position).getIdShop());
                    startActivity(intent);
                }
            });
        return rootView;
    }
}

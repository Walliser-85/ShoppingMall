package com.example.simploncenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AllShops extends Fragment {
    ListView lst;
    String[] shopname ={"Migros", "C&A", "H&M", "Interdiscount"};
    String[] desc ={"This is Migros", "This is C&A", "This is H&M", "This is Interdiscount"};
    Integer[] articles = {10, 20, 30, 40};
    Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        // Inflate the layout for this fragment
            lst= (ListView)rootView.findViewById(R.id.lwShops);
            CustomListView customListView = new CustomListView(getActivity(),shopname,desc,imgid,articles);
            lst.setAdapter(customListView);
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getActivity(), CurrentShop.class));
                }
            });
        return rootView;
    }
}

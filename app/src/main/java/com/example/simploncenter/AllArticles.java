package com.example.simploncenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.simploncenter.Adapter.CustomListViewArticle;

public class AllArticles extends Fragment {

    ListView lst;
    String[] articlename ={"Banane", "Shirt", "Hoodie", "CD"};
    String[] desc ={"Banane from Africa", "green smart shirt", "a good looking Hoodie", "Good music on one cd"};
    Integer[] prices = {2, 10, 20, 25};
    Integer[] imgid={R.drawable.banana, R.drawable.shirt, R.drawable.mango, R.drawable.cd};
    CustomListViewArticle customListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_articles, container, false);
        // Inflate the layout for this fragment
        lst= (ListView)rootView.findViewById(R.id.lwArticl);
        customListView = new CustomListViewArticle(getActivity(),articlename,desc,imgid,prices);
        lst.setAdapter(customListView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(getActivity(), currentShop.class));
            }
        });

        EditText theFilter = (EditText) rootView.findViewById(R.id.SearchFilter);
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }
}

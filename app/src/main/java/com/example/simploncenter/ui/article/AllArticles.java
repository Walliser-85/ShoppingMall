package com.example.simploncenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.simploncenter.Adapter.CustomListViewArticle;
import com.example.simploncenter.viewmodel.article.ListViewAllArticle;

import java.util.ArrayList;
import java.util.Arrays;

public class AllArticles extends Fragment {

    ListView lst;
    ArrayList<String> articlename =new ArrayList<String>(Arrays.asList("Banane", "Shirt", "Hoodie", "CD"));
    ArrayList<String> desc =new ArrayList<String>(Arrays.asList("Banane from Africa", "green smart shirt", "a good looking Hoodie", "Good music on one cd"));
    ArrayList<Integer> prices =new ArrayList<Integer>(Arrays.asList(2, 10, 20, 25));
    ArrayList<Integer> imgid=new ArrayList<Integer>(Arrays.asList(R.drawable.banana, R.drawable.shirt, R.drawable.mango, R.drawable.cd));

    ListViewAllArticle customListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_articles, container, false);
        // Inflate the layout for this fragment
        lst= (ListView)rootView.findViewById(R.id.ArticlesAll);
        customListView = new ListViewAllArticle(getActivity(),articlename,desc,imgid,prices);
        lst.setAdapter(customListView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startActivity(new Intent(getActivity(), CurrentArticle.class));
            }
        });

        //filter
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
                //neue
                ArrayList<String> articlenameN =new ArrayList<String>();
                ArrayList<String> descN =new ArrayList<String>();
                ArrayList<Integer> pricesN =new ArrayList<Integer>();
                ArrayList<Integer> imgidN = new ArrayList<Integer>();
                int index;

                for (String name:articlename
                     ) {
                    if (name.contains(s)){
                        index=articlename.indexOf(name);
                        articlenameN.add(articlename.get(index));
                        descN.add(desc.get(index));
                        pricesN.add(prices.get(index));
                        imgidN.add(imgid.get(index));
                    }
                }

                //neue Liste
                customListView = new ListViewAllArticle(getActivity(),articlenameN,descN,imgidN,pricesN);
                lst.setAdapter(customListView);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //startActivity(new Intent(getActivity(), CurrentArticle.class));
                    }
                });

            }
        });


        return rootView;
    }
}

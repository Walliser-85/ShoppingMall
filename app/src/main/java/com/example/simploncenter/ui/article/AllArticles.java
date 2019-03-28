package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.simploncenter.Adapter.ArticleAdapter;
import com.example.simploncenter.Adapter.ShopAdapter;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.repository.ShopRepository;
import com.example.simploncenter.ui.shop.CurrentShop;
import com.example.simploncenter.viewmodel.article.ListViewAllArticle;

import java.util.ArrayList;
import java.util.List;

public class AllArticles extends Fragment {
    private ListView listview;
    private List<ArticleEntity> articleList;
    private ListViewAllArticle viewModel;

    ListView lst;
    private ShopRepository repository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_articles, container, false);
        // Inflate the layout for this fragment

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_article);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        articleList = new ArrayList<ArticleEntity>();

        final ArticleAdapter adapter = new ArticleAdapter(articleList, new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArticleEntity item) {
                Intent intent = new Intent(getActivity(), CurrentArticle.class);
                intent.putExtra("articleId", item.getIdArticle());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        ListViewAllArticle.Factory factory = new ListViewAllArticle.Factory(getActivity().getApplication(),0);
        viewModel = ViewModelProviders.of(this, factory).get(ListViewAllArticle.class);
        viewModel.getArticles().observe(this, articleEntities -> {
            if (articleEntities != null) {
                adapter.setArticle(articleEntities);
            }
        });
        return rootView;
    }
}


/*
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
*/
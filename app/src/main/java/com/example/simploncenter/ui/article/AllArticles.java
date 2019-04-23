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
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.repository.ShopRepository;
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
                intent.putExtra("shopId", item.getToShop());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        ListViewAllArticle.Factory factory = new ListViewAllArticle.Factory(getActivity().getApplication(),"");
        viewModel = ViewModelProviders.of(this, factory).get(ListViewAllArticle.class);
        viewModel.getArticles().observe(this, articleEntities -> {
            if (articleEntities != null) {
                adapter.setArticle(articleEntities);
            }
        });
        return rootView;
    }
}


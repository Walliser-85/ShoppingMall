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
import com.example.simploncenter.db.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private List<ArticleEntity> articleList = new ArrayList<>();
            Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};
    private final OnItemClickListener listener;

    public ArticleAdapter(List<ArticleEntity> articleList,OnItemClickListener listener) {
            this.articleList = articleList;
            this.listener = listener;
            }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.listview_layout, viewGroup, false);
            return new ArticleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder articleHolder, int i) {
            ArticleEntity currentArticle = articleList.get(i);
            articleHolder.textViewArticleName.setText(currentArticle.getArticleName());
            articleHolder.textViewArticleDescription.setText(currentArticle.getDescription());
            articleHolder.ivw.setImageBitmap(BitmapFactory.decodeByteArray(articleList.get(i).getPicture(), 0, articleList.get(i).getPicture().length));
            articleHolder.bind(articleList.get(i), listener);
    }

    @Override
    public int getItemCount() {
            return articleList.size();
            }

    public void setArticle(List<ArticleEntity> shop) {
            this.articleList = shop;
            notifyDataSetChanged();
    }

    public ArticleEntity getArticleAt(int position) {
            return articleList.get(position);
    }

    class ArticleHolder extends RecyclerView.ViewHolder{
        private TextView textViewArticleName;
        private TextView textViewArticleDescription;
        private ImageView ivw;


        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            textViewArticleName = itemView.findViewById(R.id.tvshopname);
            textViewArticleDescription = itemView.findViewById(R.id.tvdescription);
            ivw = itemView.findViewById(R.id.imageView);
        }

        public void bind(final ArticleEntity article, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(article);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(ArticleEntity item);

    }
}

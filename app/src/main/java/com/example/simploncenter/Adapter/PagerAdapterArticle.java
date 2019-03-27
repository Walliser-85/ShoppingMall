package com.example.simploncenter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.simploncenter.ui.article.AllArticles;
import com.example.simploncenter.ui.article.Article;
import com.example.simploncenter.ui.article.CreateArticle;

public class PagerAdapterArticle extends FragmentPagerAdapter {
    int numberOfTabs;

    public PagerAdapterArticle(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Article tab1 = new Article();
                return tab1;
            case 1:
                AllArticles tab2 = new AllArticles();
                return tab2;
            case 2:
                CreateArticle tab3 = new CreateArticle();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

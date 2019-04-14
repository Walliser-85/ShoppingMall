package com.example.simploncenter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.simploncenter.ui.article.AllArticles;
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
                AllArticles tab1 = new AllArticles();
                return tab1;
            case 1:
                CreateArticle tab2 = new CreateArticle();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}

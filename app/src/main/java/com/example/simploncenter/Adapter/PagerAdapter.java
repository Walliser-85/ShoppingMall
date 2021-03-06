package com.example.simploncenter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.simploncenter.ui.shop.AllShops;
import com.example.simploncenter.ui.shop.CreateShop;

public class PagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllShops tab1 = new AllShops();
                return tab1;
            case 1:
                CreateShop tab2 = new CreateShop();
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

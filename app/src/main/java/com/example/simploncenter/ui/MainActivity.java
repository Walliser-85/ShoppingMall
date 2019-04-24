package com.example.simploncenter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.simploncenter.R;
import com.example.simploncenter.ui.article.Articles;
import com.example.simploncenter.ui.shop.Shops;

public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.content_main, frameLayout);

        setTitle(getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(getString(R.string.app_name));
        navigationView.setCheckedItem(R.id.nav_home);
    }

    public void goToShops(View view){
        Intent intent = new Intent(this, Shops.class);
        startActivity(intent);
    }

    public void goToArticles(View view){
        Intent intent = new Intent(this, Articles.class);
        startActivity(intent);
    }
}

package com.example.simploncenter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.simploncenter.R;
import com.example.simploncenter.db.repository.ShopRepository;
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
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

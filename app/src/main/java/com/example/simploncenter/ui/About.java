package com.example.simploncenter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.simploncenter.R;
import com.example.simploncenter.ui.article.Articles;
import com.example.simploncenter.ui.shop.Shops;

import static com.example.simploncenter.R.id.drawer_layout;

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.about);
        navigationView.setCheckedItem(R.id.nav_about);
        getLayoutInflater().inflate(R.layout.content_about, frameLayout);
    }
}

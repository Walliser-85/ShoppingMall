package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simploncenter.db.AppDatabase;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.ui.About;
import com.example.simploncenter.Adapter.PagerAdapterArticle;
import com.example.simploncenter.ui.BaseActivity;
import com.example.simploncenter.ui.MainActivity;
import com.example.simploncenter.R;
import com.example.simploncenter.ui.shop.Shops;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;
import com.example.simploncenter.viewmodel.shop.ShopListViewModel;

import java.io.ByteArrayOutputStream;

public class Articles extends BaseActivity {

    private ArticleViewModel viewModel;
    private static final String TAG = "EditArticleActivity";
    private ShopListViewModel ShopviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.flContent);

        drawerLayout = findViewById(R.id.base_drawer_layout_tab);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.base_nav_view_tab);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle(R.string.articles);
        getLayoutInflater().inflate(R.layout.content_articles, frameLayout);
        navigationView.setCheckedItem(R.id.nav_articles);

        //TAB Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Article per Shop"));
        tabLayout.addTab(tabLayout.newTab().setText("All Articles"));
        tabLayout.addTab(tabLayout.newTab().setText("New Articles"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterArticle adapter = new PagerAdapterArticle
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    public void createNewArticle(View view) {
        EditText articleName = findViewById(R.id.txt_article_name);
        EditText articleDescription = findViewById(R.id.txt_article_description);
        EditText articleShortDescription = findViewById(R.id.txt_article_ShortDescription);
        EditText articlePrice = findViewById(R.id.txt_article_price);
        ImageView image = findViewById(R.id.imageViewArticle);
        Bitmap img = ((BitmapDrawable)image.getDrawable()).getBitmap();
        Spinner spinner=findViewById(R.id.spinnerShopNames);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        Log.d(TAG, "###IMAGE###" + image.getDrawable());
        if(articleName.getText().equals("@string/article_name") || articleDescription.getText().equals("@string/article_description")){
            Toast.makeText(Articles.this, "Fill out all the Data!!", Toast.LENGTH_SHORT).show();
        }
        else {
            //spinner wert in id umwandeln
            ShopEntity selectShop=(ShopEntity) spinner.getSelectedItem();
            int idShop=selectShop.getIdShop();

            ArticleEntity newArticle = new ArticleEntity(String.valueOf(articleName.getText()),idShop, String.valueOf(articleDescription.getText()),
                    String.valueOf(articleShortDescription.getText()),Float.parseFloat(articlePrice.getText().toString()), byteArray);

            ArticleViewModel.Factory factory = new ArticleViewModel.Factory(
                    getApplication(), 0);
            viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
            viewModel.createArticle(newArticle, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createArticle: success");
                    Toast toast = Toast.makeText(Articles.this, "Created a new article", Toast.LENGTH_LONG);
                    toast.show();
                    Intent h=new Intent (Articles.this, Articles.class);
                    startActivity(h);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create article: failure", e);
                }
            });
        }
    }*/
}

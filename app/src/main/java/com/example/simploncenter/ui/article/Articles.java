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
import com.example.simploncenter.ui.MainActivity;
import com.example.simploncenter.R;
import com.example.simploncenter.ui.shop.Shops;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;

import java.io.ByteArrayOutputStream;

public class Articles extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArticleViewModel viewModel;
    private static final String TAG = "EditArticleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TAB Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_article);
        tabLayout.addTab(tabLayout.newTab().setText("Article per Shop"));
        tabLayout.addTab(tabLayout.newTab().setText("All Articles"));
        tabLayout.addTab(tabLayout.newTab().setText("New Articles"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_article);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent h=new Intent (Articles.this, MainActivity.class);
            startActivity(h);
        } else if (id == R.id.nav_shops) {
            Intent h=new Intent (Articles.this, Shops.class);
            startActivity(h);
        } else if (id == R.id.nav_articles) {
            Intent h=new Intent (Articles.this, Articles.class);
            startActivity(h);
        } else if (id == R.id.nav_about) {
            Intent h=new Intent (Articles.this, About.class);
            startActivity(h);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
            AppDatabase db = AppDatabase.getInstance(this.getBaseContext());
            int idShop = db.shopDao().getId(spinner.getSelectedItem().toString());

            ArticleEntity newArticle = new ArticleEntity(String.valueOf(articleName.getText()),idShop, String.valueOf(articleDescription.getText()),
                    String.valueOf(articleShortDescription.getText()),Float.parseFloat(articlePrice.getText().toString()), byteArray);

            ArticleViewModel.Factory factory = new ArticleViewModel.Factory(
                    getApplication(), 0);
            viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
            viewModel.createArticle(newArticle, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createShop: success");
                    Toast toast = Toast.makeText(Articles.this, "Create a New Shop", Toast.LENGTH_LONG);
                    toast.show();
                    Intent h=new Intent (Articles.this, Shops.class);
                    startActivity(h);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createShop: failure", e);
                }
            });
        }
    }
}

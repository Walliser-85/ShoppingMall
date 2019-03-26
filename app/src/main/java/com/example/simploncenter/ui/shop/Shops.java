package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.simploncenter.ui.About;
import com.example.simploncenter.Adapter.PagerAdapter;
import com.example.simploncenter.ui.article.Articles;
import com.example.simploncenter.ui.MainActivity;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

import java.io.ByteArrayOutputStream;

public class Shops extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ShopViewModel viewModel;
    private static final String TAG = "EditShopActivity";

    ListView lst;
    String[] shopname ={"Migros", "C&A", "H&M", "Interdiscount"};
    String[] desc ={"This is Migros", "This is C&A", "This is H&M", "This is Interdiscount"};
    Integer[] articles = {10, 20, 30, 40};
    Integer[] imgid={R.drawable.migros, R.drawable.ca, R.drawable.hm, R.drawable.interdiscount};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //TAB Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("All Shops"));
        tabLayout.addTab(tabLayout.newTab().setText("New Shop"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
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
            Intent h=new Intent (Shops.this, MainActivity.class);
            startActivity(h);
        } else if (id == R.id.nav_shops) {
            Intent h=new Intent (Shops.this, Shops.class);
            startActivity(h);
        } else if (id == R.id.nav_articles) {
            Intent h=new Intent (Shops.this, Articles.class);
            startActivity(h);
        } else if (id == R.id.nav_about) {
            Intent h=new Intent (Shops.this, About.class);
            startActivity(h);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createNewShop(View view) {
        EditText shopName = findViewById(R.id.txt_shop_name);
        EditText shopDescription = findViewById(R.id.txt_shop_description);
        ImageView image = findViewById(R.id.imageViewShop);
        Bitmap img = ((BitmapDrawable)image.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        Log.d(TAG, "###IMAGE###" + image.getDrawable());
        if(shopName.getText().equals("@string/shop_name") || shopDescription.getText().equals("@string/shop_description")){
            Toast.makeText(Shops.this, "Fill out all the Data!!", Toast.LENGTH_SHORT).show();
        }
        else {
            ShopEntity newShop = new ShopEntity(String.valueOf(shopName.getText()), String.valueOf(shopDescription.getText()), byteArray);

            ShopViewModel.Factory factory = new ShopViewModel.Factory(
                    getApplication(), 0);
            viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel.class);
            viewModel.createShop(newShop, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createShop: success");
                    Toast toast = Toast.makeText(Shops.this, "Create a New Shop", Toast.LENGTH_LONG);
                    toast.show();
                    Intent h=new Intent (Shops.this, Shops.class);
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

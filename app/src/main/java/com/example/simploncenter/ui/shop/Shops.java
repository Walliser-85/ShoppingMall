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
import com.example.simploncenter.ui.BaseActivity;
import com.example.simploncenter.ui.article.Articles;
import com.example.simploncenter.ui.MainActivity;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

import java.io.ByteArrayOutputStream;

public class Shops extends BaseActivity {

    private ShopViewModel viewModel;
    private static final String TAG = "EditShopActivity";

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

        setTitle(R.string.shops);
        getLayoutInflater().inflate(R.layout.content_shops, frameLayout);
        navigationView.setCheckedItem(R.id.nav_shops);

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

    public void createNewShop(View view) {
        EditText shopName = findViewById(R.id.txt_shop_name);
        EditText shopDescription = findViewById(R.id.txt_shop_description);
        ImageView image = findViewById(R.id.imageViewShop);
        if (image.getTag().equals("1")){
            Toast.makeText(Shops.this, "Select a picture!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap img = ((BitmapDrawable)image.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        Log.d(TAG, "###IMAGE###" + image.getDrawable());
        if(shopName.getText().toString().isEmpty() || shopDescription.getText().toString().isEmpty()){
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

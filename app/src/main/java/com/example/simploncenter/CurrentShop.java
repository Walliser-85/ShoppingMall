package com.example.simploncenter;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.Adapter.CustomListView;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

import java.text.NumberFormat;

public class CurrentShop extends AppCompatActivity {
    private ShopEntity shop;
    private ShopViewModel viewModel;
    private TextView titel, description;
    private ImageView picture;
    private int shopId;

    ListView lst;
    String[] shopname ={"Apple", "Banana", "Grapes", "Mango", "Watermelon"};
    String[] desc ={"This is Migros", "This is C&A", "This is H&M", "This is Interdiscount", "This is Interdiscount"};
    Integer[] articles = {10, 20, 30, 40, 50};
    Integer[] imgid={R.drawable.apple, R.drawable.banana, R.drawable.grapes, R.drawable.mango, R.drawable.watermelon};

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Toast.makeText(CurrentShop.this, "EDIT", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_delete) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete Shop");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Are your sure to Delete this Shop");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                viewModel.deleteClient(shop, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "sdfd", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();

            return true;
        }
        if (id == R.id.action_add) {
            Toast.makeText(CurrentShop.this, "ADD", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_shop);

        shopId = getIntent().getIntExtra("shopId",0);

        initiateView();

        ShopViewModel.Factory factory = new ShopViewModel.Factory(getApplication(),shopId);
        viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel.class);
        viewModel.getShop().observe(this, shopEntity -> {
            if (shopEntity != null) {
                shop = shopEntity;
                updateContent();
            }
        });
        /*lst= (ListView)findViewById(R.id.lwShopsCurrent);
        CustomListView customListView = new CustomListView(this,shopname,desc,imgid,articles);
        lst.setAdapter(customListView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CurrentShop.this, shopname[position], Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void initiateView() {
        titel = findViewById(R.id.txvTitel);
        description = findViewById(R.id.txvDescription);
        picture = findViewById(R.id.imgCurrentShop);
    }

    private void updateContent(){
        if(shop != null){
            titel.setText(shop.getShopName());
            description.setText(shop.getDescription());
            picture.setImageResource(R.drawable.apple);
        }
    }
}

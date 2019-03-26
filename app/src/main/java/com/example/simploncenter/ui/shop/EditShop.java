package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

public class EditShop extends AppCompatActivity {
    private ShopEntity shop;
    private ShopViewModel viewModel;
    private TextView titel, description;
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);

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
    }

    public void SaveShop(View view) {
        shop.setShopName(String.valueOf(titel.getText()));
        shop.setDescription(String.valueOf(description.getText()));
        viewModel.updateShop(shop, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditShop.this, "Save changes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditShop.this, CurrentShop.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EditShop.this, "Cannot save changes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateView() {
        titel = findViewById(R.id.txt_shop_name_edit);
        description = findViewById(R.id.txt_shop_description_edit);
    }

    private void updateContent(){
        if(shop != null){
            titel.setText(shop.getShopName());
            description.setText(shop.getDescription());
        }
    }
}

package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.ui.BaseActivity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;
import com.example.simploncenter.viewmodel.article.ListViewAllArticle;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditShop extends BaseActivity {
    private ShopEntity shop;
    private ShopViewModel viewModel;
    private TextView titel, description;
    private int shopId;
    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Context context;

    private ListViewAllArticle viewModelArticle;
    private ArticleViewModel viewModelArticleEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_shops);
        getLayoutInflater().inflate(R.layout.activity_edit_shop, frameLayout);

        initiateView();

        shopId = getIntent().getIntExtra("shopId",0);

        ShopViewModel.Factory factory = new ShopViewModel.Factory(getApplication(),shopId);
        viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel.class);
        viewModel.getShop().observe(this, shopEntity -> {
            if (shopEntity != null) {
                shop = shopEntity;
                updateContent();
                setTitle("Edit " + shop.getShopName());
            }
        });


        context = getApplicationContext();

        Button pickImage = (Button) findViewById(R.id.btn_pick_edit);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
    }

    public void SaveShop(View view) {
        shop.setShopName(String.valueOf(titel.getText()));
        shop.setDescription(String.valueOf(description.getText()));

        Bitmap img = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        shop.setPicture(byteArray);

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

        //Change Article Shopnames too
        ListViewAllArticle.Factory factoryA = new ListViewAllArticle.Factory(getApplication(),String.valueOf(titel.getText()));
        viewModelArticle = ViewModelProviders.of(this, factoryA).get(ListViewAllArticle.class);
        //Article ViewModel for Update
        ArticleViewModel.Factory factoryArtDel = new ArticleViewModel.Factory(getApplication(),shopId);
        viewModelArticleEd = ViewModelProviders.of(this, factoryArtDel).get(ArticleViewModel.class);

        viewModelArticle.getArticlesByShop().observe(this, articleEntities -> {
            if (articleEntities != null) {
                for (ArticleEntity arEnt:articleEntities
                ) {
                    arEnt.setToShop(String.valueOf(titel.getText()));
                    viewModelArticleEd.updateArticle(arEnt, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(Exception e) {}
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void initiateView() {
        titel = findViewById(R.id.txt_shop_name_edit);
        description = findViewById(R.id.txt_shop_description_edit);
        imageView = findViewById(R.id.imageViewShopEdit);
    }

    private void updateContent(){
        if(shop != null){
            titel.setText(shop.getShopName());
            description.setText(shop.getDescription());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(shop.getPicture(), 0, shop.getPicture().length));
        }
    }
}

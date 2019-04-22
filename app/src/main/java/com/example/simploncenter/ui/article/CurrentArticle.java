package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.repository.ArticleRepository;
import com.example.simploncenter.ui.BaseActivity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;
import com.example.simploncenter.viewmodel.shop.ShopListViewModel;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CurrentArticle extends BaseActivity {
    private ArticleEntity article;
    private ArticleViewModel viewModel;
    private ShopListViewModel viewModelShop;
    private ShopViewModel viewModelShopName;
    private TextView titel, description, shopname, price;
    private ImageView picture;
    private String articleId;
    private String getShopID;
    private static final int EDIT_ARTICLE = 1;
    private static final int DELETE_ARTICLE = 2;
    private ArticleRepository repository;
    private String shopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_articles);
        getLayoutInflater().inflate(R.layout.activity_current_article, frameLayout);

        articleId = getIntent().getExtras().getString("articleId","0");

        initiateView();

        ArticleViewModel.Factory factory = new ArticleViewModel.Factory(getApplication(),articleId);
        viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
        viewModel.getArticle().observe(this, articleEntity -> {
            if (articleEntity != null) {
                article = articleEntity;
                getShopID = article.getToShop();
                updateContent();
                setTitle(article.getArticleName());
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, EDIT_ARTICLE, Menu.NONE, "EDIT ARTICLE")
                .setIcon(R.drawable.ic_pencil)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, DELETE_ARTICLE, Menu.NONE, "DELETE ARTICLE")
                .setIcon(R.drawable.ic_delete_button)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == EDIT_ARTICLE) {
            Toast.makeText(CurrentArticle.this, "EDIT", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CurrentArticle.this, EditArticle.class);
            intent.putExtra("articleId", articleId);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == DELETE_ARTICLE) {
            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete article");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Are your sure to delete this article");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                viewModel.deleteArticle(article, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Intent h=new Intent (CurrentArticle.this, Articles.class);
                        startActivity(h);
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initiateView() {
        titel = findViewById(R.id.txvTitelArticle);
        shopname = findViewById(R.id.txvShopNameArticle);
        description = findViewById(R.id.txvArticleDescription);
        picture = findViewById(R.id.imgCurrentArticle);
        price = findViewById(R.id.txvPrice);
    }

    private void updateContent(){
        if(article != null){
            //ShopName
            ShopViewModel.Factory factoryShop = new ShopViewModel.Factory(getApplication(),getShopID);
            viewModelShopName = ViewModelProviders.of(this, factoryShop).get(ShopViewModel.class);
            viewModelShopName.getShop().observe(this, shopEntity -> {
                if (shopEntity != null) {
                    shopName=shopEntity.getShopName();
                    shopname.setText(shopName);
                }
            });

            titel.setText(article.getArticleName());
            description.setText(article.getDescription());
            price.setText("Price: "+Float.toString(article.getPrice())+" CHF");

            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            // Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child("articles/"+article.getIdArticle()+".png");

            final long ONE_MEGABYTE = 1024 * 1024 * 5;
            pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    picture.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });

        }
    }
}

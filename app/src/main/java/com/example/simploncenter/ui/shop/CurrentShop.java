package com.example.simploncenter.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.Adapter.ArticleAdapter;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.db.repository.ArticleRepository;
import com.example.simploncenter.ui.BaseActivity;
import com.example.simploncenter.ui.article.CurrentArticle;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;
import com.example.simploncenter.viewmodel.article.ListViewAllArticle;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CurrentShop extends BaseActivity {
    private ShopEntity shop;
    private ShopViewModel viewModel;
    private ArticleViewModel viewModelArticleDelet;
    private List<ArticleEntity> articleList;
    private ListViewAllArticle viewModelArticle;
    private TextView titel, description;
    private ImageView picture;
    private ArrayList<ArticleEntity> articles;
    private static final int EDIT_SHOP = 1;
    private static final int DELETE_SHOP = 2;
    private static final int ADD_ARTICLE = 3;
    private String shopId;
    private String shopName;
    private ArticleRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.setCheckedItem(R.id.nav_shops);
        getLayoutInflater().inflate(R.layout.activity_current_shop, frameLayout);

        shopId = getIntent().getExtras().getString("shopId","0");
        shopName= getIntent().getExtras().getString("shopName","defaultKey");

        initiateView();



        //Article ViewModel for Delete
        ArticleViewModel.Factory factoryArtDel = new ArticleViewModel.Factory(getApplication(),shopId);
        viewModelArticleDelet = ViewModelProviders.of(this, factoryArtDel).get(ArticleViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_article);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        articleList = new ArrayList<ArticleEntity>();

        final ArticleAdapter adapter = new ArticleAdapter(articleList, new ArticleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArticleEntity item) {
                Intent intent = new Intent(CurrentShop.this, CurrentArticle.class);
                intent.putExtra("articleId", item.getIdArticle());
                intent.putExtra("shopId", shopName);
                startActivity(intent);
            }
        });
        //Get articles for the views

        ArrayList<String> arrayIds=new ArrayList<>();
        recyclerView.setAdapter(adapter);
        ShopViewModel.Factory factory = new ShopViewModel.Factory(getApplication(),shopId);
        viewModel = ViewModelProviders.of(this, factory).get(ShopViewModel.class);
        viewModel.getShop().observe(this, shopEntity -> {
            if (shopEntity != null) {
                shop = shopEntity;
                updateContent();
                setTitle(shop.getShopName());
                //Artikel Ids
                for(String key : shop.getArticles().keySet())
                {
                    arrayIds.add(key);
                }
                this.articles=new ArrayList<>();
                ListViewAllArticle.Factory factoryA = new ListViewAllArticle.Factory(getApplication(), shopId);
                viewModelArticle = ViewModelProviders.of(this, factoryA).get(ListViewAllArticle.class);
                for (String id: arrayIds) {
                    viewModelArticle.getArticle(id).observe(this, articleEntities -> {
                        System.out.print(articleEntities);
                        if (articleEntities != null) {
                            this.articles.add(articleEntities);
                            adapter.setArticle(this.articles);
                        }
                    });
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, EDIT_SHOP, Menu.NONE, "EDIT SHOP")
                .setIcon(R.drawable.ic_pencil)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, DELETE_SHOP, Menu.NONE, "DELETE SHOP")
                .setIcon(R.drawable.ic_delete_button)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, ADD_ARTICLE, Menu.NONE, "ADD ARTICLE")
                .setIcon(R.drawable.ic_plus)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == EDIT_SHOP) {
            Toast.makeText(CurrentShop.this, "EDIT", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CurrentShop.this, EditShop.class);
            intent.putExtra("shopId", shopId);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == DELETE_SHOP) {

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Delete Shop");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Are your sure to Delete this Shop");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                viewModel.deleteClient(shop, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Intent h=new Intent (CurrentShop.this, Shops.class);
                        startActivity(h);
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });

                for (ArticleEntity a: this.articles
                     ) {
                    viewModelArticleDelet.deleteArticle(a, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(Exception e) {}
                    });
                }


            });


            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();

            return true;
        }
        if (item.getItemId() == ADD_ARTICLE) {
            Toast.makeText(CurrentShop.this, "ADD New Article", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
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
            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();
            // Create a reference with an initial file path and name
            StorageReference pathReference = storageRef.child("shops/"+shop.getIdShop()+".png");

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

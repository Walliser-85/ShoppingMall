package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.Adapter.CustomListViewArticle;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;

public class CurrentArticle extends AppCompatActivity {
    private ArticleEntity article;
    private ArticleViewModel viewModel;
    private TextView titel, description;
    private ImageView picture;
    private int shopId;

    ListView lst;
    String[] articlename ={"Apple", "Banana", "Grapes", "Mango", "Watermelon"};
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
            Toast.makeText(CurrentArticle.this, "EDIT", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CurrentArticle.this, EditArticle.class);
            intent.putExtra("shopId", shopId);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete) {
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
        if (id == R.id.action_add) {
            Toast.makeText(CurrentArticle.this, "ADD New Article", Toast.LENGTH_SHORT).show();

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

        ArticleViewModel.Factory factory = new ArticleViewModel.Factory(getApplication(),shopId);
        viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
        viewModel.getArticle().observe(this, articleEntity -> {
            if (articleEntity != null) {
                article = articleEntity;
                updateContent();
            }
        });

        lst= (ListView)findViewById(R.id.lwShopsCurrent);
        CustomListViewArticle customListView = new CustomListViewArticle(this, articlename,desc,imgid,articles);
        lst.setAdapter(customListView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CurrentArticle.this, articlename[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiateView() {
        titel = findViewById(R.id.txvTitel);
        description = findViewById(R.id.txvDescription);
        picture = findViewById(R.id.imgCurrentShop);
    }

    private void updateContent(){
        if(article != null){
            titel.setText(article.getArticleName());
            description.setText(article.getDescription());
            picture.setImageBitmap(BitmapFactory.decodeByteArray(article.getPicture(), 0, article.getPicture().length));
        }
    }
}

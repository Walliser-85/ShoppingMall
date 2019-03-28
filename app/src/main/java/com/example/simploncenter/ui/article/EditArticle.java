package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditArticle extends AppCompatActivity {
    private ArticleEntity article;
    private ArticleViewModel viewModel;
    private TextView titel, description;
    private int articleId;
    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);

        articleId = getIntent().getIntExtra("articleId", 0);

        initiateView();

        ArticleViewModel.Factory factory = new ArticleViewModel.Factory(getApplication(), articleId);
        viewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);
        viewModel.getArticle().observe(this, articleEntity -> {
            if (articleEntity != null) {
                article = articleEntity;
                updateContent();
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
        article.setArticleName(String.valueOf(titel.getText()));
        article.setDescription(String.valueOf(description.getText()));

        Bitmap img = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();

        article.setPicture(byteArray);

        viewModel.updateArticle(article, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(EditArticle.this, "Save changes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditArticle.this, CurrentArticle.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(EditArticle.this, "Cannot save changes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
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

    private void updateContent() {
        if (article != null) {
            titel.setText(article.getArticleName());
            description.setText(article.getDescription());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(article.getPicture(), 0, article.getPicture().length));
        }
    }
}
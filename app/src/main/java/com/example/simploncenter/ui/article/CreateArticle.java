package com.example.simploncenter.ui.article;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simploncenter.Adapter.ListAdapter;
import com.example.simploncenter.R;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;
import com.example.simploncenter.viewmodel.article.ArticleViewModel;
import com.example.simploncenter.viewmodel.shop.ShopListViewModel;
import com.example.simploncenter.viewmodel.shop.ShopViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CreateArticle extends Fragment {
    private static final String TAG = "CreateArticle";
    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Context context;
    public Spinner spinner;
    private ShopListViewModel viewModel;
    private ArticleViewModel articleViewModel;
    private ShopViewModel viewModelAdd;
    private ListAdapter<String> adpaterShopList;
    private ShopEntity shop;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_article, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.imageViewArticle);

        context = getActivity().getApplicationContext();

        Button pickImage = (Button) rootView.findViewById(R.id.btn_pickA);
        pickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);

            }
        });


        //Spinner
        this.spinner = (Spinner) rootView.findViewById(R.id.spinnerShopNames);
        this.adpaterShopList = new ListAdapter<>(CreateArticle.this.getContext(), R.layout.row_shops, new ArrayList<>());
        this.spinner.setAdapter(adpaterShopList);
        setupViewModels();

        Button createArticleB = rootView.findViewById(R.id.btnCreateArticle);
        createArticleB.setOnClickListener(view -> {
            CreateArticle.this.createNewArticle(rootView);
        });

        return rootView;
    }

    private void setupViewModels() {

        ShopListViewModel.Factory factory = new ShopListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ShopListViewModel.class);

        viewModel.getShops().observe(this, shopEntity -> {
            if (shopEntity != null) {
                //Array shopnames
                ArrayList<String> shopNames = new ArrayList<String>();
                for (ShopEntity s : shopEntity
                ) {
                    shopNames.add(s.getShopName());
                }
                updateAdapterShopList(shopNames);
            }
        });

    }

    private void updateAdapterShopList(List<String> shopNames) {
        adpaterShopList.updateData(new ArrayList<>(shopNames));
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
                        imageView.setTag(2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public boolean createNewArticle(View view) {
        EditText articleName = view.findViewById(R.id.txt_article_name);
        EditText articleDescription = view.findViewById(R.id.txt_article_description);
        EditText articleShortDescription = view.findViewById(R.id.txt_article_ShortDescription);
        EditText articlePrice = view.findViewById(R.id.txt_article_price);
        ImageView image = view.findViewById(R.id.imageViewArticle);
        if (image.getTag().equals("1")) {
            Toast.makeText(CreateArticle.this.getContext(), "Select a picture!!", Toast.LENGTH_SHORT).show();
            return true;
        }
        Bitmap img = ((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Log.d(TAG, "###IMAGE###" + image.getDrawable());
        if (articleName.getText().toString().isEmpty() || articleDescription.getText().toString().isEmpty() ||
                articleShortDescription.getText().toString().isEmpty() || articlePrice.getText().toString().isEmpty()) {
            Toast.makeText(CreateArticle.this.getContext(), "Fill out all the Data!!", Toast.LENGTH_SHORT).show();
        } else {
            View b = view.findViewById(R.id.btnCreateArticle);
            b.setVisibility(View.GONE);
            String selectShop = (String) spinner.getSelectedItem();
            //ID vom Shop
            final String[] toShop = new String[1];
            viewModel.getShops().observe(this, shopEntity -> {
                if (shopEntity != null) {
                    for (ShopEntity sho : shopEntity
                    ) {
                        if (sho.getShopName().equals(selectShop)) {
                            toShop[0] = sho.getIdShop();
                        }
                    }
                }
            });
            ArticleEntity newArticle = new ArticleEntity(toShop[0], String.valueOf(articleName.getText()), String.valueOf(articleDescription.getText()),
                    String.valueOf(articleShortDescription.getText()), Float.parseFloat(articlePrice.getText().toString()));

            ArticleViewModel.Factory factoryA = new ArticleViewModel.Factory(getActivity().getApplication(), "0");
            articleViewModel = ViewModelProviders.of(this, factoryA).get(ArticleViewModel.class);
            articleViewModel.createArticle(newArticle, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createArticle: success");
                    Toast toast = Toast.makeText(CreateArticle.this.getContext(), "Created a new article", Toast.LENGTH_LONG);
                    toast.show();
                    Intent h = new Intent(CreateArticle.this.getContext(), Articles.class);
                    startActivity(h);
                    b.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "create article: failure", e);
                    b.setVisibility(View.VISIBLE);
                }
            }, byteArray);
        }

            return true;

    }
}


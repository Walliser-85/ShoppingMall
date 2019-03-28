package com.example.simploncenter.ui.article;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.simploncenter.R;
import com.example.simploncenter.db.AppDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CreateArticle extends Fragment implements AdapterView.OnItemSelectedListener {
    private final int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Context context;
    public Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_article, container, false);

        imageView = (ImageView)rootView.findViewById(R.id.imageViewArticle);

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
        spinner = (Spinner) rootView.findViewById(R.id.spinnerShopNames);
        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();

        return rootView;
    }

    private void loadSpinnerData() {
        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());
        LiveData<List<String>> shopnames = db.shopDao().getAllShopNames();

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter<String>(CreateArticle.this.getContext(),android.R.layout.simple_spinner_item);
        dataAdapter.addAll(shopnames);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

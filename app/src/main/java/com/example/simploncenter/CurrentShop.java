package com.example.simploncenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simploncenter.Adapter.CustomListView;

public class CurrentShop extends AppCompatActivity {
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
            Toast.makeText(CurrentShop.this, "DELETE", Toast.LENGTH_SHORT).show();
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
        lst= (ListView)findViewById(R.id.lwShopsCurrent);
        CustomListView customListView = new CustomListView(this,shopname,desc,imgid,articles);
        lst.setAdapter(customListView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CurrentShop.this, shopname[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}

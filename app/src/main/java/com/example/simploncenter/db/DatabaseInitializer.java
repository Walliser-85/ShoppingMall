package com.example.simploncenter.db;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.db.entity.ShopEntity;

import java.sql.SQLClientInfoException;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addShop(final AppDatabase db, final String shopName, final String description, final Bitmap picture) throws SQLClientInfoException {
        ShopEntity shop = new ShopEntity(shopName, description, picture);
        db.shopDao().insert(shop);
    }

    private static void addArticle(final AppDatabase db, final String name, final String description,
                                   final int idShop, final float price, final Bitmap picture) throws SQLClientInfoException {
        ArticleEntity article = new ArticleEntity(name, description, idShop, price, picture);
        db.articleDao().insert(article);
    }

    private static void populateWithTestData(AppDatabase db) throws SQLClientInfoException {
        db.shopDao().deleteAll();

        Bitmap picture = null;

        addShop(db,
                "H&M", "test description", picture
        );
        addShop(db,
                "C&A", "test description", picture
        );
        addShop(db,
                "Migros", "test description", picture
        );
        addShop(db,
                "Interdiscount", "test description", picture
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*addAccount(db,
                "Savings", 20000d, "m.p@fifa.com"
        );
        addAccount(db,
                "Savings", 20000d, "s.b@fifa.com"
        );
        addAccount(db,
                "Savings", 20000d, "e.s@fifa.com"
        );
        addAccount(db,
                "Savings", 20000d, "a.c@fifa.com"
        );

        addAccount(db,
                "Secret", 1820000d, "m.p@fifa.com"
        );
        addAccount(db,
                "Secret", 1820000d, "s.b@fifa.com"
        );
        addAccount(db,
                "Secret", 1820000d, "e.s@fifa.com"
        );
        addAccount(db,
                "Secret", 1820000d, "a.c@fifa.com"
        );*/
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {
                populateWithTestData(database);
            } catch (SQLClientInfoException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}

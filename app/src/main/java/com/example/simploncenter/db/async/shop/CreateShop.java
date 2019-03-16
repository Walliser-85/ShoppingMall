package com.example.simploncenter.db.async.shop;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

import java.sql.SQLClientInfoException;

public class CreateShop extends AsyncTask<ShopEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateShop(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ShopEntity... shopEntities) {
        try {
            for (ShopEntity client : shopEntities)
                ((BaseApp) application).getDatabase().shopDao()
                        .insert(client);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

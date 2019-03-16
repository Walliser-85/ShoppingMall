package com.example.simploncenter.db.async.shop;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

public class UpdateShop extends AsyncTask<ShopEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateShop(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(ShopEntity... params) {
        try {
            for (ShopEntity client : params)
                ((BaseApp) application).getDatabase().shopDao()
                        .update(client);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}

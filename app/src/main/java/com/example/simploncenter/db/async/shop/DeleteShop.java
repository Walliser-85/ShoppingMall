package com.example.simploncenter.db.async.shop;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ShopEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

public class DeleteShop extends AsyncTask<ShopEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteShop(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ShopEntity... params) {
        try {
            for (ShopEntity client : params)
                ((BaseApp) application).getDatabase().shopDao()
                        .delete(client);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

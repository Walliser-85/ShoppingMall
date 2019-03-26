package com.example.simploncenter.db.async.article;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

public class DeleteArticle extends AsyncTask<ArticleEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteArticle(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ArticleEntity... params) {
        try {
            for (ArticleEntity client : params)
                ((BaseApp) application).getDatabase().articleDao()
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

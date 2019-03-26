package com.example.simploncenter.db.async.article;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

public class UpdateArticle extends AsyncTask<ArticleEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public UpdateArticle(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(ArticleEntity... params) {
        try {
            for (ArticleEntity client : params)
                ((BaseApp) application).getDatabase().articleDao()
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

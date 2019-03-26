package com.example.simploncenter.db.async.article;

import android.app.Application;
import android.os.AsyncTask;

import com.example.simploncenter.BaseApp;
import com.example.simploncenter.db.entity.ArticleEntity;
import com.example.simploncenter.util.OnAsyncEventListener;

public class CreateArticle extends AsyncTask<ArticleEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateArticle(Application application, OnAsyncEventListener callback){
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ArticleEntity... articleEntities) {
        try {
            for (ArticleEntity article : articleEntities)
                ((BaseApp) application).getDatabase().articleDao()
                        .insert(article);
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

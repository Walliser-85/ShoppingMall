package com.example.simploncenter.db.repository;

public class ArticleRepository {
    private static ArticleRepository instance;

    private ArticleRepository() {

    }

    public static ArticleRepository getInstance() {
        if (instance == null) {
            synchronized (ArticleRepository.class) {
                if (instance == null) {
                    instance = new ArticleRepository();
                }
            }
        }
        return instance;
    }
}

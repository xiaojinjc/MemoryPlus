package com.example.memoryplus.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.entities.Category;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao categoryDao;

    public CategoryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        categoryDao = db.categoryDao();
    }

//    public void insert(Category category) {
//        new Thread(() -> categoryDao.insert(category)).start();
//    }
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

        public void insert(Category category) {
            executor.execute(() -> categoryDao.insert(category));
        }


    public void update(Category category) {
        new Thread(() -> categoryDao.update(category)).start();
    }

    public void delete(Category category) {
        new Thread(() -> categoryDao.delete(category)).start();
    }

    public LiveData<List<Category>> getAll() {
        return categoryDao.getAll();
    }

    public Category getById(int id) {
        return categoryDao.getById(id);
    }

}

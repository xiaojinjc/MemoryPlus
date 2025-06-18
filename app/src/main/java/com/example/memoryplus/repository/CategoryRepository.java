package com.example.memoryplus.repository;

import android.content.Context;
import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.model.Category;
import java.util.List;

public class CategoryRepository {
    private final CategoryDao categoryDao;

    public CategoryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        categoryDao = db.categoryDao();
    }

    public void insert(Category category) {
        new Thread(() -> categoryDao.insert(category)).start();
    }

    public void update(Category category) {
        new Thread(() -> categoryDao.update(category)).start();
    }

    public void delete(Category category) {
        new Thread(() -> categoryDao.delete(category)).start();
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }

    public Category getById(int id) {
        return categoryDao.getById(id);
    }
}

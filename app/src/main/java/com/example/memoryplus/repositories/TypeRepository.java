package com.example.memoryplus.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.TypeDao;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.entities.TypeWithCategory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TypeRepository {
    private final TypeDao typeDao;

    public TypeRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        typeDao = db.typeDao();
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void insert(Type type) {
        executor.execute(() -> typeDao.insert(type));
    }

    public void update(Type type) {
        executor.execute(() -> typeDao.update(type));
    }

    public void delete(TypeWithCategory typeWithCategory) {
        executor.execute(() -> typeDao.delete(typeWithCategory.type));
    }

    public LiveData<List<Type>> getAll() {
        return typeDao.getAll();
    }

    public Type getById(int id) {
        return typeDao.getById(id);
    }

    public List<Type> getByCategoryId(Integer categoryId) {
        return typeDao.getByCategoryId(categoryId);
    }

    public LiveData<List<TypeWithCategory>> getAllWithCategories(){
        return typeDao.getAllWithCategories();
    }
}

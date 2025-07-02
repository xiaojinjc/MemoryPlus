package com.example.memoryplus.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.TypeDao;
import com.example.memoryplus.entities.Type;
import java.util.List;

public class TypeRepository {
    private final TypeDao typeDao;

    public TypeRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        typeDao = db.typeDao();
    }

    public void insert(Type type) {
        new Thread(() -> typeDao.insert(type)).start();
    }

    public void update(Type type) {
        new Thread(() -> typeDao.update(type)).start();
    }

    public void delete(Type type) {
        new Thread(() -> typeDao.delete(type)).start();
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
}

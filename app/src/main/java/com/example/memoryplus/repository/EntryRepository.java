package com.example.memoryplus.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.EntryDao;
import com.example.memoryplus.model.EntryDB;
import java.util.Date;
import java.util.List;

public class EntryRepository {
    private final EntryDao entryDao;

    public EntryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        entryDao = db.entryDao();
    }

    public void insert(EntryDB entry) {
        new Thread(() -> entryDao.insert(entry)).start();
    }

    public void update(EntryDB entry) {
        new Thread(() -> entryDao.update(entry)).start();
    }

    public void delete(EntryDB entry) {
        new Thread(() -> entryDao.delete(entry)).start();
    }

    public LiveData<List<EntryDB>> getAllLive() {
        return entryDao.getAllLive();
    }


    public EntryDB getById(int id) {
        return entryDao.getById(id);
    }

    public List<EntryDB> getByCategoryId(Integer categoryId) {
        return entryDao.getByCategoryId(categoryId);
    }

    public List<EntryDB> getByTypeId(Integer typeId) {
        return entryDao.getByTypeId(typeId);
    }

    public List<EntryDB> getByDate(long dateMillis) {
        return entryDao.getByDate(dateMillis);
    }
}

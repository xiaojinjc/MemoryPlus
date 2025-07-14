package com.example.memoryplus.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.EntryDao;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;

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

    public List<EntryDB> getByTypeId(Integer typeId) {
        return entryDao.getByTypeId(typeId);
    }

//    Date: long or String??
    public List<EntryDB> getByDate(String dateMillis) {
        return entryDao.getByDate(dateMillis);
    }

    public LiveData<List<EntryWithType>> getAllEntriesWithTypes(){
        return entryDao.getAllWithType();
    }
}

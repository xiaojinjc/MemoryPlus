package com.example.memoryplus.repository;

import android.content.Context;
import com.example.memoryplus.AppDatabase;
import com.example.memoryplus.dao.SuggestionDao;
import com.example.memoryplus.entity.Suggestion;
import java.util.List;

public class SuggestionRepository {
    private final SuggestionDao suggestionDao;

    public SuggestionRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        suggestionDao = db.suggestionDao();
    }

    public void insert(Suggestion suggestion) {
        new Thread(() -> suggestionDao.insert(suggestion)).start();
    }

    public void update(Suggestion suggestion) {
        new Thread(() -> suggestionDao.update(suggestion)).start();
    }

    public void delete(Suggestion suggestion) {
        new Thread(() -> suggestionDao.delete(suggestion)).start();
    }

    public List<Suggestion> getByTypeId(int typeId) {
        return suggestionDao.getByTypeId(typeId);
    }
}

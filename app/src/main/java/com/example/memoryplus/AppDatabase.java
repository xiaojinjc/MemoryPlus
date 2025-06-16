package com.example.memoryplus;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.dao.EntryDao;
import com.example.memoryplus.dao.SuggestionDao;
import com.example.memoryplus.dao.TypeDao;

import com.example.memoryplus.model.Category;
import com.example.memoryplus.model.EntryDB;
import com.example.memoryplus.model.Suggestion;
import com.example.memoryplus.model.Type;

@Database(entities = {Category.class, Type.class, Suggestion.class, EntryDB.class}, version = 1)
//@TypeConverters({Converters.class}) // You’ll need this for Date
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract TypeDao typeDao();
    public abstract SuggestionDao suggestionDao();
    public abstract EntryDao entryDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "diary.db")
                            .addMigrations()  // Replace with .addMigrations() in real use
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.example.memoryplus;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.dao.EntryDao;
import com.example.memoryplus.dao.SuggestionDao;
import com.example.memoryplus.dao.TypeDao;

import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.Suggestion;
import com.example.memoryplus.entities.Type;

@Database(entities = {Category.class, Type.class, Suggestion.class, EntryDB.class}, version = 1)
//@TypeConverters({Converters.class}) // You’ll need this for Date
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract TypeDao typeDao();
    public abstract SuggestionDao suggestionDao();
    public abstract EntryDao entryDao();

    private static volatile AppDatabase INSTANCE;


//    Delete and install app again if there is migration error
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "diary.db")
                            .fallbackToDestructiveMigrationFrom()  // Replace with .addMigrations() in real use
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static final RoomDatabase.Callback prepopulateCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            Executors.newSingleThreadExecutor().execute(() -> {
//                // Insert sample categories, types, and entries here using DAOs
//
//                AppDatabase database = INSTANCE;
//
//                CategoryDao categoryDao = database.categoryDao();
//                TypeDao typeDao = database.typeDao();
//                EntryDao entryDao = database.entryDao();
//
//                // Sample Categories
//                categoryDao.insert(new Category("Work"));
//                categoryDao.insert(new Category("Study"));
//
//                // Sample Types
//                typeDao.insert(new Type(  "Meeting"));
//                typeDao.insert(new Type( "Reading"));
//
//                // Sample Entries (make sure to match your EntryDB constructor)
//                entryDao.insert(new EntryDB("15/06/25", 2, 2, "DP", 1, false, "Notes 1"));
//                entryDao.insert(new EntryDB("15/06/25", 2, 2, "Algorithm", 0, true, "Notes 2"));
//                entryDao.insert(new EntryDB("16/06/26", 1, 1, "Project Update", 2, false, "Notes 3"));
//            });
//        }
//    };

}

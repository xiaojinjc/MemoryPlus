package com.example.memoryplus;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.dao.EntryDao;
import com.example.memoryplus.dao.SuggestionDao;
import com.example.memoryplus.dao.TypeDao;

import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.Suggestion;
import com.example.memoryplus.entities.Type;

import java.util.concurrent.Executors;

@Database(
        entities = {Category.class, Type.class, Suggestion.class, EntryDB.class},
        version = 2
        )
//@TypeConverters({Converters.class}) // You’ll need this for Date
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract TypeDao typeDao();
    public abstract SuggestionDao suggestionDao();
    public abstract EntryDao entryDao();

    private static volatile AppDatabase INSTANCE;

    public static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                db.execSQL("INSERT INTO categories (name) VALUES ('Uncategorized')");
                db.execSQL("INSERT INTO types (name, categoryId) VALUES ('Untyped', 1)");
                db.execSQL("INSERT INTO entries (typeId, description, date, part, notes, isComplete) " +
                        "VALUES (1, 'Test description', '2025-06-01', '1/', 'Some notes here', 1)");
                db.execSQL("INSERT INTO entries (typeId, description, date, part, notes, isComplete)" +
                        "VALUES (1, 'WHAT DU HELLI', '2025-07-06', '1/', 'EXTRA', 0)");
                db.execSQL("INSERT INTO entries (typeId, description, date, part, notes, isComplete)" +
                        "VALUES (1, 'WHAT DU HELLI', '2025-06-02', '1/', 'EXTRA', 0)");
                db.execSQL("INSERT INTO entries (typeId, description, date, part, notes, isComplete)" +
                        "VALUES (1, 'WHAT DU HELLI', '2025-06-01', '1/', 'EXTRA', 0)");
            });
        }
    };


//    Delete and install app again if there is migration error
//    TODO: Add migration strategy to database
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "diary.db")
                            .fallbackToDestructiveMigrationFrom()  // Replace with .addMigrations() in real use
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

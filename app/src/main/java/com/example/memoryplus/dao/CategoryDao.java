package com.example.memoryplus.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memoryplus.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    Category getById(int id);

    @Query("SELECT id FROM categories WHERE name = 'Uncategorized' LIMIT 1")
    int getUncategorizedCategoryId();
}

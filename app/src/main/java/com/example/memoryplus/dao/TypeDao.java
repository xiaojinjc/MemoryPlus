package com.example.memoryplus.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memoryplus.entities.Type;
import com.example.memoryplus.entities.TypeWithCategory;

import java.util.List;

@Dao
public interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Type type);

    @Update
    void update(Type type);

    @Delete
    void delete(Type type);

    @Query("SELECT * FROM types WHERE categoryId = :categoryId")
    List<Type> getByCategoryId(Integer categoryId);  // allow null if SET_NULL is used

    @Query("SELECT * FROM types WHERE id = :id LIMIT 1")
    Type getById(int id);

    @Query("SELECT * FROM types ORDER BY name ASC")
    LiveData<List<Type>> getAll();

    @Query("SELECT * FROM types")
    LiveData<List<TypeWithCategory>> getAllWithCategories();

    @Query("UPDATE types SET categoryId = :newCategoryId WHERE categoryId = :oldCategoryId")
    void updateTypesToCategory(int oldCategoryId, int newCategoryId);

}

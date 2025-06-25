package com.example.memoryplus.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memoryplus.entity.Suggestion;

import java.util.List;

@Dao
public interface SuggestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Suggestion suggestion);

    @Update
    void update(Suggestion suggestion);

    @Delete
    void delete(Suggestion suggestion);

    @Query("SELECT * FROM suggestions WHERE typeId = :typeId")
    List<Suggestion> getByTypeId(int typeId);
}


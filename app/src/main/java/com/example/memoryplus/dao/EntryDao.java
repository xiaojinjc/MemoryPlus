package com.example.memoryplus.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memoryplus.model.EntryDB;

import java.util.Date;
import java.util.List;

@Dao
public interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(EntryDB entry);

    @Update
    void update(EntryDB entry);

    @Delete
    void delete(EntryDB entry);

    @Query("SELECT * FROM entries ORDER BY date DESC")
    LiveData<List<EntryDB>> getAllLive();

    @Query("SELECT * FROM entries WHERE id = :id")
    EntryDB getById(int id);

    @Query("SELECT * FROM entries WHERE categoryId = :categoryId")
    List<EntryDB> getByCategoryId(Integer categoryId);

    @Query("SELECT * FROM entries WHERE typeId = :typeId")
    List<EntryDB> getByTypeId(Integer typeId);

    @Query("SELECT * FROM entries WHERE date = :date")
    List<EntryDB> getByDate(long date);
}

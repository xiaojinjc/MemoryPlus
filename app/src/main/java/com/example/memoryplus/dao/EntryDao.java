package com.example.memoryplus.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;

import java.util.List;

@Dao
public interface EntryDao {
//    @upsert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(EntryDB entry);

    @Update
    void update(EntryDB entry);

    @Delete
    void delete(EntryDB entry);

    @Query("SELECT * FROM entries ORDER BY date DESC")
    LiveData<List<EntryDB>> getAllLive();

    @Query("SELECT * FROM entries ORDER BY date DESC")
    LiveData<List<EntryWithType>> getAllWithType();

    @Query("SELECT * FROM entries WHERE id = :id")
    EntryDB getById(int id);

    @Query("SELECT * FROM entries WHERE typeId = :typeId")
    List<EntryDB> getByTypeId(Integer typeId);

//    Date: long or String
    @Query("SELECT * FROM entries WHERE date = :date")
    List<EntryDB> getByDate(String date);

    @Query("UPDATE entries SET typeId = :newTypesId WHERE typeId = :oldTypesId")
    void updateEntriesToType(int oldTypesId, int newTypesId);


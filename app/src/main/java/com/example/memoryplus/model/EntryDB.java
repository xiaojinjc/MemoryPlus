package com.example.memoryplus.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "entries")
public class EntryDB {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date;

    @NonNull
    public String category;

    public String type;

    @NonNull
    public String description;

    public int partNumber;
    public boolean isComplete;

    public String notes;

    // Constructor
    public EntryDB(@NonNull String date, @NonNull String category, String type, @NonNull String description, int partNumber, boolean isComplete, String notes) {
        this.date = date;
        this.category = category;
        this.type = type;
        this.description = description;
        this.partNumber = partNumber;
        this.isComplete = isComplete;
        this.notes = notes;
    }
}

package com.example.memoryplus.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

// TODO: Optional, change column names

@Entity(tableName = "entries",
        foreignKeys = {
                @ForeignKey(entity = Type.class,
                        parentColumns = "id",
                        childColumns = "typeId",
                        onDelete = ForeignKey.SET_NULL)
        },
        indices = {@Index("typeId")})
public class EntryDB {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date;

    public  int typeId;

    @NonNull
    public String description;

    public String part;
    public boolean isComplete;

    public String notes;

    // Constructor
    public EntryDB(@NonNull String date, int typeId, @NonNull String description, String part, boolean isComplete, String notes) {
        this.date = date;
        this.typeId = typeId;
        this.description = description;
        this.part = part;
        this.isComplete = isComplete;
        this.notes = notes;
    }
}



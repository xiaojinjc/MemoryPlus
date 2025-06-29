package com.example.memoryplus.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

// TODO: Optional, change column names

@Entity(tableName = "entries",
        foreignKeys = {
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "categoryId",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Type.class,
                        parentColumns = "id",
                        childColumns = "typeId",
                        onDelete = ForeignKey.SET_NULL)
        },
        indices = {@Index("categoryId"), @Index("typeId")})
public class EntryDB {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date;

    @NonNull
    public int categoryId;

    public  int typeId;

    @NonNull
    public String description;

    public int partNumber;
    public boolean isComplete;

    public String notes;

    // Constructor
    public EntryDB(@NonNull String date, @NonNull int categoryId, int typeId, @NonNull String description, int partNumber, boolean isComplete, String notes) {
        this.date = date;
        this.categoryId = categoryId;
        this.typeId = typeId;
        this.description = description;
        this.partNumber = partNumber;
        this.isComplete = isComplete;
        this.notes = notes;
    }
}



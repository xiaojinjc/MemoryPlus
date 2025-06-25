package com.example.memoryplus.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name; // e.g., Media, Outside, Games, Food

    public Category(@NonNull String name){
        this.name = name;
    }
}

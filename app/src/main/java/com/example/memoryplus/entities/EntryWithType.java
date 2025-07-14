package com.example.memoryplus.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class EntryWithType {
    @Embedded
    public EntryDB entryDB;

    @Relation(
            parentColumn = "typeId",
            entityColumn = "id"
    )
    public Type type;
}

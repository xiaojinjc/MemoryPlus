package com.example.memoryplus.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.items.HeaderItem;
import com.example.memoryplus.items.ListItem;
import com.example.memoryplus.repositories.EntryRepository;

import java.util.ArrayList;
import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private final EntryRepository repository;
    private final LiveData<List<EntryDB>> allEntries; // raw data from DB
    private final MediatorLiveData<List<ListItem>> groupedItems = new MediatorLiveData<>();

    public EntryViewModel(@NonNull Application application) {
        super(application);
        repository = new EntryRepository(application);
        allEntries = repository.getAllLive();

        groupedItems.addSource(allEntries, entries -> {
            groupedItems.setValue(groupEntries(entries));
        });
    }

    public LiveData<List<ListItem>> getGroupedItems() {
        return groupedItems;
    }

    private List<ListItem> groupEntries(List<EntryDB> entries) {
        List<ListItem> grouped = new ArrayList<>();
        if (entries == null || entries.isEmpty()) return grouped;

        String lastDate = "";
        for (EntryDB entry : entries) {
            if (!entry.date.equals(lastDate)) {
                // For header, pinned = false for now, completed = entry.isComplete of first item in group
                grouped.add(new HeaderItem(entry.date, false, entry.isComplete));
                lastDate = entry.date;
            }

            String typeName = "Type" + entry.typeId; // Replace with real lookup

//            String part = entry.part > 0 ? entry.part + "/" : "";

//            grouped.add(new EntryItem(typeName, entry.description, part, entry.notes));
        }
        return grouped;
    }
}


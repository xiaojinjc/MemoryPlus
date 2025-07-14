package com.example.memoryplus.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;
import com.example.memoryplus.repositories.EntryRepository;
import com.example.memoryplus.items.EntryItem;
import com.example.memoryplus.items.HeaderItem;
import com.example.memoryplus.items.ListItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EntryViewModel_2 extends AndroidViewModel {
    private final EntryRepository repository;
    LiveData<List<EntryWithType>> allEntriesWithTypes;


    public EntryViewModel_2(@NonNull Application application) {
        super(application);
        repository = new EntryRepository(application);
        allEntriesWithTypes = repository.getAllEntriesWithTypes();
    }

    public LiveData<List<EntryWithType>> getAllEntriesWithTypes() {
        return allEntriesWithTypes;
    }

    public void insertEntry(EntryDB entry) {
        repository.insert(entry);
    }

    public void deleteEntry(EntryDB entry) {
        repository.delete(entry);
    }

    public LiveData<List<EntryWithType>> getEntriesWithTypeForMonth (int year, int month) {
        return repository.getAllEntriesWithTypeForMonth(year, month);
    }
}

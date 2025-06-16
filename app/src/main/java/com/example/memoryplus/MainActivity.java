package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.memoryplus.adapters.EntryAdapter;
//import com.example.memoryplus.model.Entry;
import com.example.memoryplus.utils.EntryStorage;
import com.example.memoryplus.utils.FilenameUtils;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private List<Entry> entries;
    private RecyclerView recyclerView;
//    private EntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        List<Entry> sampleEntries = new ArrayList<>();
//        sampleEntries.add(new Entry("2025-06-14", "Media", "Film", "Example Movie", "Nice"));
//        sampleEntries.add(new Entry("2025-06-15", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-16", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-17", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-18", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-12", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-18", "Game", "GI", "In the long lens Event 1", ""));
//        sampleEntries.add(new Entry("2025-06-18", "Game", "GI", "In the long lens Event 1", ""));
//        EntryStorage.saveEntries(this, "2025.json", sampleEntries);
//
//
////         Load entries
//        String filename = FilenameUtils.getFilenameForYear(2025);
//        entries = EntryStorage.loadEntries(this, filename);
//        Log.d("MainActivity", "Loaded entries: " + entries.size());
//
////         Setting up recyclerView
//        recyclerView = findViewById(R.id.entryRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new EntryAdapter(entries);
//        recyclerView.setAdapter(adapter);
    }
}
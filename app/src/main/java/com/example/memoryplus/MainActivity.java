package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.memoryplus.adapters.EntryAdapter;
//import com.example.memoryplus.model.Entry;
import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.utils.EntryStorage;
import com.example.memoryplus.utils.FilenameUtils;
import com.example.memoryplus.viewmodel.EntryViewModel;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private List<Entry> entries;
    private RecyclerView recyclerView;
//    private EntryAdapter adapter;
    private  EntryViewModel entryViewModel;
    private EntryGroupedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.entryRecyclerView);
        adapter = new EntryGroupedAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        entryViewModel.getGroupedItems().observe(this, groupedItems -> {
            adapter.setItems(groupedItems);
        });
    }
}
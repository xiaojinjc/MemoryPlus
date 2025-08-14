package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.memoryplus.adapters.SearchAdapter;
import com.example.memoryplus.viewmodels.EntryViewModel_2;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ImageButton backSearchButton;
    private ImageButton searchButton;
    private EditText searchInput;
    private RecyclerView recyclerView;

    private SearchAdapter adapter;
    private EntryViewModel_2 entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backSearchButton = findViewById(R.id.search_back_button);
        searchButton = findViewById(R.id.search_search);
        searchInput = findViewById(R.id.search_input);

        adapter = new SearchAdapter(new ArrayList<>());

        recyclerView = findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel_2.class);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        backSearchButton.setOnClickListener(v -> {
            finish();
        });

    }

    private void search(String query) {
        entryViewModel.searchEntries(query).observe(this, results -> {
            adapter.updateData(results);
        });
    }
}
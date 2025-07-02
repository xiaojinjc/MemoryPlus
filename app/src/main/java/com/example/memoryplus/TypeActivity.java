package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.memoryplus.entities.Category;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.TypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class TypeActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_activity);

        TypeViewModel typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);
        CategoryViewModel catViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        spinner = findViewById(R.id.typeCatListSelect);

        catViewModel.getAllCategories().observe(this, categories -> {
            Log.d("SpinnerDebug", "Observed " + categories.size() + " categories");
            updateSpinner(categories);
        });
    }

    private void updateSpinner(List<Category> categories) {

        List<String> categoryNames = new ArrayList<>();
        for (Category c : categories) {
            categoryNames.add(c.name);
            Log.d("SpinnerDebug", "category" + categoryNames.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
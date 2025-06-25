package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.memoryplus.adapters.CategoryAdapter;
import com.example.memoryplus.dao.CategoryDao;
import com.example.memoryplus.entity.Category;
import com.example.memoryplus.viewmodel.CategoryViewModel;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        AppDatabase database = AppDatabase.getInstance(this);
        CategoryDao catDao = database.categoryDao();

        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.categoryDisplay);
        CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getAllCategories().observe(this, categories -> {
            Log.d("CategoryObserver", "Received category list of size: " + categories.size());
            adapter.setItemList(categories);
        });

        Button addCategory = findViewById(R.id.addCategory);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insertCategory(new Category("Outside"));
            }
        });

        Button delCategory = findViewById(R.id.delCategory);
        delCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteCategory(new Category("Outside"));
            }
        });

    }


}
package com.example.memoryplus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memoryplus.adapters.CategoryAdapter;
import com.example.memoryplus.entities.Category;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoryViewModel viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.categoryList);
        CategoryAdapter adapter = new CategoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getAllCategories().observe(this, categories -> {
            Log.d("CategoryObserver", "Received category list of size: " + categories.size());
            adapter.setItemList(categories);
        });


        FloatingActionButton createCat = findViewById(R.id.create_cat_fab);
        createCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateCatPopup();
            }
        });

        adapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onDeleteClick(Category category) {
                new AlertDialog.Builder(CategoryActivity.this)
                        .setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete \"" + category.name + "\"?" +
                                "\n\nThis will change all types related to "+ category.name + " to Uncategorized.")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            viewModel.deleteCategory(category);
                        }))
                        .setNegativeButton("No", null)
                        .show();
            }

            @Override
            public void onEditClick(Category category) {
                CategoryViewModel viewModel = new ViewModelProvider(CategoryActivity.this).get(CategoryViewModel.class);

                View popupView = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.popup_create_cat, null);
                Context wrapper = new ContextThemeWrapper(CategoryActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(wrapper);
                builder.setView(popupView);
                builder.setTitle("Edit Category");

                EditText categoryInput = popupView.findViewById(R.id.categoryInput);
                categoryInput.setText(category.name);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String catName = categoryInput.getText().toString().trim();
                        List<Category> currentList = viewModel.getAllCategories().getValue();

//                        Validation
                        if (catName.isEmpty()) {
                            Toast.makeText(CategoryActivity.this, "Category name can't be empty.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (catName.length() > 30) {
                            Toast.makeText(CategoryActivity.this, "Category name can't exceed 30 characters.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (currentList != null) {
                            boolean duplicate = false;
                            for (Category c : currentList) {
                                if (c.name.equalsIgnoreCase(catName)) {
                                    duplicate = true;
                                    break;
                                }
                            }

                            if (duplicate) {
                                Toast.makeText(CategoryActivity.this, "Category " + catName + " already exists.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        category.name = catName;
                        viewModel.updateCategory(category);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void showCreateCatPopup() {
        View popupView = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.popup_create_cat, null);
        Context wrapper = new ContextThemeWrapper(CategoryActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(wrapper);
        builder.setView(popupView);
        builder.setTitle("Create Category");

        EditText categoryInput = popupView.findViewById(R.id.categoryInput);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String catName = categoryInput.getText().toString().trim();
                CategoryViewModel viewModel = new ViewModelProvider(CategoryActivity.this).get(CategoryViewModel.class);

                List<Category> currentList = viewModel.getAllCategories().getValue();

                if (catName.isEmpty()) {
                    Toast.makeText(CategoryActivity.this, "Category name can't be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (catName.length() > 30) {
                    Toast.makeText(CategoryActivity.this, "Category name can't exceed 30 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (currentList != null) {
                    boolean duplicate = false;
                    for (Category c : currentList) {
                        if (c.name.equalsIgnoreCase(catName)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        Toast.makeText(CategoryActivity.this, "Category " + catName + " already exists.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Category newCategory = new Category(catName);
                viewModel.insertCategory(newCategory);
                categoryInput.setText("");

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
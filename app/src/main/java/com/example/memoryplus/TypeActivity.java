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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.memoryplus.adapters.TypeAdapter;
import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.entities.TypeWithCategory;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.TypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        TypeViewModel typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.type_cat_list);
        TypeAdapter adapter = new TypeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        typeViewModel.getAllTypesWithCategories().observe(this, typesWithCategory -> {
            Log.d("CategoryObserver", "Received category list of size: " + typesWithCategory.size());
            adapter.setItemList(typesWithCategory);
        });

        FloatingActionButton createCat = findViewById(R.id.create_type_fab);
        createCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateCatPopup();
            }
        });

        adapter.setOnTypeClickListener(new TypeAdapter.OnTypeClickListener() {
            @Override
            public void onDeleteClick(TypeWithCategory typeWithCategory) {
                new AlertDialog.Builder(TypeActivity.this)
                        .setTitle("Delete Category")
                        .setMessage("Are you sure you want to delete \"" + typeWithCategory.type.name + "\"?")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            typeViewModel.deleteType(typeWithCategory);
                        }))
                        .setNegativeButton("No", null)
                        .show();
            }

            @Override
            public void onUpdateClick(Type type) {
                TypeViewModel typeViewModel = new ViewModelProvider(TypeActivity.this).get(TypeViewModel.class);
                CategoryViewModel catViewModel = new ViewModelProvider(TypeActivity.this).get(CategoryViewModel.class);

                View popupView = LayoutInflater.from(TypeActivity.this).inflate(R.layout.popup_create_type, null);
                Context wrapper = new ContextThemeWrapper(TypeActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(wrapper);
                builder.setView(popupView);
                builder.setTitle("Create Type");

                EditText typeInput = popupView.findViewById(R.id.typeInput);
                Spinner spinner = popupView.findViewById(R.id.popup_cat_type_spinner);

                typeInput.setText(type.name);

                catViewModel.getAllCategories().observe(TypeActivity.this, categories -> {
                    Log.d("SpinnerDebug", "Observed " + categories.size() + " categories");
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(TypeActivity.this, android.R.layout.simple_spinner_dropdown_item, categories);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

//                    Find the index of the category id that matches the type being edited, and set it
                    int selectedIndex = 0;
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).id == type.categoryId) {
                            selectedIndex = i;
                            break;
                        }
                    }

                    spinner.setSelection(selectedIndex);
                });

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String typeName = typeInput.getText().toString().trim();
                        List<TypeWithCategory> currentList = typeViewModel.getAllTypesWithCategories().getValue();

//                Validation
                        if (spinner.getSelectedItem() == null){
                            Toast.makeText(TypeActivity.this, "No category has been selected.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (typeName.isEmpty()){
                            Toast.makeText(TypeActivity.this, "Type name can't be empty.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Category cat = (Category) spinner.getSelectedItem();

                        Log.d("AddType", "currentList size: " + (currentList == null ? "null" : currentList.size()));
                        if (currentList != null){
                            Log.d("AddType", "enter main if");
                            boolean duplicate = false;
                            for (TypeWithCategory t : currentList) {
                                if (t.type.name.equalsIgnoreCase(typeName) && t.category.id == cat.id){
                                    duplicate = true;
                                    Log.d("AddType", "duplicate found");
                                    break;
                                }
                            }

                            if (duplicate) {
                                Log.d("AddType", "reach duplicate if");
                                Toast.makeText(TypeActivity.this, "No duplicate types of the same category can be added.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        type.name = typeName;
                        type.categoryId = cat.id;
                        typeViewModel.updateType(type);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void showCreateCatPopup() {
        TypeViewModel typeViewModel = new ViewModelProvider(TypeActivity.this).get(TypeViewModel.class);
        CategoryViewModel catViewModel = new ViewModelProvider(TypeActivity.this).get(CategoryViewModel.class);

        View popupView = LayoutInflater.from(TypeActivity.this).inflate(R.layout.popup_create_type, null);
        Context wrapper = new ContextThemeWrapper(TypeActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(wrapper);
        builder.setView(popupView);
        builder.setTitle("Create Type");

        EditText typeInput = popupView.findViewById(R.id.typeInput);
        Spinner spinner = popupView.findViewById(R.id.popup_cat_type_spinner);

        catViewModel.getAllCategories().observe(TypeActivity.this, categories -> {
            Log.d("SpinnerDebug", "Observed " + categories.size() + " categories");
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(TypeActivity.this, android.R.layout.simple_spinner_dropdown_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        });

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String typeName = typeInput.getText().toString().trim();
                List<TypeWithCategory> currentList = typeViewModel.getAllTypesWithCategories().getValue();

//                Validation
                if (spinner.getSelectedItem() == null){
                    Toast.makeText(TypeActivity.this, "No category has been selected.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (typeName.isEmpty()){
                    Toast.makeText(TypeActivity.this, "Type name can't be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Category cat = (Category) spinner.getSelectedItem();

                Log.d("AddType", "currentList size: " + (currentList == null ? "null" : currentList.size()));
                if (currentList != null){
                    Log.d("AddType", "enter main if");
                    boolean duplicate = false;
                    for (TypeWithCategory t : currentList) {
                        if (t.type.name.equalsIgnoreCase(typeName) && t.category.id == cat.id){
                            duplicate = true;
                            Log.d("AddType", "duplicate found");
                            break;
                        }
                    }

                    if (duplicate) {
                        Log.d("AddType", "reach duplicate if");
                        Toast.makeText(TypeActivity.this, "No duplicate types of the same category can be added.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Type newType = new Type(cat.id, typeName);
                typeViewModel.insertType(newType);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
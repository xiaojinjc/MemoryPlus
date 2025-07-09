package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.memoryplus.repositories.TypeRepository;
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

        RecyclerView recyclerView = findViewById(R.id.typeCatList);
        TypeAdapter adapter = new TypeAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        typeViewModel.getAllTypesWithCategories().observe(this, typesWithCategory -> {
            Log.d("CategoryObserver", "Received category list of size: " + typesWithCategory.size());
            adapter.setItemList(typesWithCategory);
        });

        Button addType = findViewById(R.id.addType);
        EditText typeInput = findViewById(R.id.typeInput);


        addType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        adapter.setOnTypeClickListener(typeWithCategory -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Category")
                    .setMessage("Are you sure you want to delete \"" + typeWithCategory.type.name + "\"?")
                    .setPositiveButton("Yes", ((dialog, which) -> {
                        typeViewModel.deleteType(typeWithCategory);
                    }))
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private void updateSpinner(List<Category> categories) {

//        List<String> categoryNames = new ArrayList<>();
//        for (Category c : categories) {
//            categoryNames.add(c.name);
//            Log.d("SpinnerDebug", "category" + categoryNames.toString());
//        }

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
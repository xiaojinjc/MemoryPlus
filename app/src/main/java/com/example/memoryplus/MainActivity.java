package com.example.memoryplus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.adapters.MonthPagerAdapter;
import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel_2;
import com.example.memoryplus.viewmodels.TypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private List<Entry> entries;
    private RecyclerView recyclerView;
//    private EntryAdapter adapter;
    private  EntryViewModel entryViewModel;
    private TypeViewModel typeViewModel;
    private EntryGroupedAdapter adapter;

    private ImageButton mainSettingButton;
    private FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView recyclerView = findViewById(R.id.entryRecyclerView);
//        adapter = new EntryGroupedAdapter();
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);
//
//        entryViewModel.getGroupedItems().observe(this, groupedItems -> {
//            adapter.setItems(groupedItems);
//        });

        ViewPager2 monthViewPager = findViewById(R.id.monthViewPager);
        MonthPagerAdapter pagerAdapter = new MonthPagerAdapter(this);
        monthViewPager.setAdapter(pagerAdapter);

        // Optional: Set current page to current month
        int startYear = 2024;
        int startMonth = 1;

        LocalDate today = LocalDate.now();
        int monthsSinceStart = (today.getYear() - startYear) * 12 + (today.getMonthValue() - startMonth);
        monthViewPager.setCurrentItem(monthsSinceStart, false);


//        Open popup menu
        mainSettingButton = findViewById(R.id.button3);
        mainSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });

//        Open create entry popup
        createButton = findViewById(R.id.createEntryFab);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreatePopup();
            }
        });

    }

    private void showMenuPopup() {
        Context wrapper = new ContextThemeWrapper(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
        PopupMenu settingPopup = new PopupMenu(wrapper, mainSettingButton);
        settingPopup.getMenuInflater().inflate(R.menu.setting_popup, settingPopup.getMenu());

        settingPopup.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.createCat)
            {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                return true;
            }
            else if (itemId == R.id.createType) {
                startActivity(new Intent(MainActivity.this, TypeActivity.class));
                return true;
            }

            return false;
        });

        settingPopup.show();
    }

    private void showCreatePopup() {
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        TypeViewModel typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);

        View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.create_entry_popup, null);
        Context wrapper = new ContextThemeWrapper(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
        builder.setView(popupView);
        builder.setMessage("Create Entry");

        EditText dateInput = popupView.findViewById(R.id.popup_date_field);
        Spinner typeInput = popupView.findViewById(R.id.popup_type_spinner);
        Spinner catInput = popupView.findViewById(R.id.popup_category_spinner);
        EditText descInput = popupView.findViewById(R.id.popup_description_field);
        EditText partInput = popupView.findViewById(R.id.popup_part_field);
        CheckBox completeInput = popupView.findViewById(R.id.popup_complete_field);
        EditText notesInput = popupView.findViewById(R.id.popup_notes_field);

        categoryViewModel.getAllCategories().observe(MainActivity.this, categories -> {
            List<Category> categoriesWithAll = new ArrayList<>(); // new list which will contain a any category
            Category temp = new Category("Any");
            temp.id = -1;
            categoriesWithAll.add(temp);
            categoriesWithAll.addAll(categories);
            ArrayAdapter<Category> catAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, categoriesWithAll);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catInput.setAdapter(catAdapter);
        });

        typeViewModel.getAllTypes().observe(this, types -> {
            List<Type> allTypes = new ArrayList<>(types);

            catInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Category selectedCat = (Category) catInput.getSelectedItem();

                    List<Type> filteredTypes;
                    if (selectedCat.id == -1){
                        filteredTypes = allTypes;
                    } else {
                        filteredTypes = new ArrayList<>();
                        for (Type t : allTypes) {
                            if (t.categoryId == selectedCat.id) {
                                filteredTypes.add(t);
                            }
                        }
                    }

                    ArrayAdapter<Type> typeAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, filteredTypes);
                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    typeInput.setAdapter(typeAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });
        });

        builder.setPositiveButton("Created", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EntryViewModel_2 entryViewModel = new ViewModelProvider(MainActivity.this).get(EntryViewModel_2.class);

                String date = dateInput.getText().toString();
                Type type = (Type) typeInput.getSelectedItem();
                String desc = descInput.getText().toString();
                String part = partInput.getText().toString();
                boolean isComplete = completeInput.isActivated();
                String notes = notesInput.getText().toString();

//                TODO: Add validation for createEntry

                EntryDB newEntry = new EntryDB(date, type.id, desc, part, isComplete, notes);
                entryViewModel.insertEntry(newEntry);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"WHATT", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
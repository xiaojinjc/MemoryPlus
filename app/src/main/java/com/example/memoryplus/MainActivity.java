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
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel;
import com.example.memoryplus.viewmodels.TypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.create_entry_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(popupView);
        AlertDialog dialog = builder.create();


        EditText dateInput = popupView.findViewById(R.id.popup_date_field);
        Spinner typeInput = popupView.findViewById(R.id.popup_type_spinner);
        Spinner catInput = popupView.findViewById(R.id.popup_category_spinner);
        EditText descInput = popupView.findViewById(R.id.popup_description_field);
        EditText partInput = popupView.findViewById(R.id.popup_part_field);
        EditText notesInput = popupView.findViewById(R.id.popup_notes_field);
        Button createButton = popupView.findViewById(R.id.popup_create);
        Button cancelButton = popupView.findViewById(R.id.popup_cancel);

        categoryViewModel.getAllCategories().observe(MainActivity.this, categories -> {
            List<Category> categoriesWithAll = new ArrayList<>();
            Category temp = new Category("Any");
            temp.id = -1;
            categoriesWithAll.add(temp);
            categoriesWithAll.addAll(categories);
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, categoriesWithAll);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catInput.setAdapter(adapter);
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String date = dateInput.getText().toString();
//                String desc = descInput.getText().toString();
//                String part = partInput.getText().toString();
//                String notes = notesInput.getText().toString();
                Log.d("CREATE", "onClick: gyatt");
            }
        });

        dialog.show();
    }
}
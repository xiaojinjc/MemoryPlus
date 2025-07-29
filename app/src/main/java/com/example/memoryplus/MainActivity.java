package com.example.memoryplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.adapters.MonthPagerAdapter;
import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel_2;
import com.example.memoryplus.viewmodels.TypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

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

        EntryViewModel_2  viewModel = new ViewModelProvider(this).get(EntryViewModel_2.class);

        TextView yearDisplay = findViewById(R.id.toolbarYearText);
        TextView monthDisplay = findViewById(R.id.month_display);
        ViewPager2 monthViewPager = findViewById(R.id.monthViewPager);
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };


        int[] currentYear = {LocalDate.now().getYear()};
        yearDisplay.setText(String.valueOf(currentYear[0]));

        MonthPagerAdapter monthPagerAdapter = new MonthPagerAdapter(this, currentYear[0]);
        monthViewPager.setAdapter(monthPagerAdapter);

        monthViewPager.setCurrentItem(LocalDate.now().getMonthValue() - 1, false);

        monthViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // position is 0 for Jan, 1 for Feb, ..., 11 for Dec
                String selectedMonth = monthNames[position];

                // Get year from the top-left TextView
                String yearText = yearDisplay.getText().toString();
                int selectedYear = 2025; // fallback

                try {
                    selectedYear = Integer.parseInt(yearText);
                } catch (NumberFormatException e) {
                    Log.e("MonthDisplay", "Invalid year format: " + yearText);
                }

                // Update the month display
                String label = selectedMonth + " " + selectedYear;
                monthDisplay.setText(label);
            }
        });

        int initialPage = monthViewPager.getCurrentItem();
        monthViewPager.post(() -> {
            monthViewPager.setCurrentItem(initialPage, false);
            monthDisplay.setText(monthNames[initialPage] + " " + yearDisplay.getText().toString());
        });



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
                showCreatePopup(null);
            }
        });

    }

//    TODO: understand this as well
    private void updateMonthText(TextView monthDisplay, List<YearMonth> months, int position) {
        if (position >= 0 && position < months.size()) {
            YearMonth ym = months.get(position);
            String formatted = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + ym.getYear();
            monthDisplay.setText(formatted);
        } else {
            monthDisplay.setText("");
        }
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

//    Optional parameter, if there is it means its edit mode
    public void showCreatePopup(@Nullable EntryWithType existingEntry) {
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        TypeViewModel typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);

        View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_create_entry, null);
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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateInput.setText(sdf.format(calendar.getTime()));

//        Show date picker dialog
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int defaultYear = calendar.get(Calendar.YEAR);
                int defaultMonth = calendar.get(Calendar.MONTH);
                int defaultDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            calendar.set(Calendar.YEAR, selectedYear);
                            calendar.set(Calendar.MONTH, selectedMonth);
                            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                            dateInput.setText(sdf.format(calendar.getTime()));
                      },
                    defaultYear, defaultMonth, defaultDay
                );
                datePickerDialog.show();
            }
        });


//        Show categories on spinner
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

//        show type on spinner, show filtered if needed
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

//        prefill values if editing
        if (existingEntry != null){
            dateInput.setText(existingEntry.entryDB.date);
            descInput.setText(existingEntry.entryDB.description);
            partInput.setText(existingEntry.entryDB.part);
            completeInput.setActivated(existingEntry.entryDB.isComplete);
            notesInput.setText(existingEntry.entryDB.notes);
        }

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
                if (existingEntry != null) {
                    newEntry.id = existingEntry.entryDB.id;
                    entryViewModel.updateEntry(newEntry);
                }
                else {
                    entryViewModel.insertEntry(newEntry);
                }

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
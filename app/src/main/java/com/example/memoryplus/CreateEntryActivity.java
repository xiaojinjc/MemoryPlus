package com.example.memoryplus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.viewmodels.CategoryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel_2;
import com.example.memoryplus.viewmodels.TypeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_entry);

        Intent intent = getIntent();
        boolean isEdit = intent.getBooleanExtra("isEdit", false);

        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        TypeViewModel typeViewModel = new ViewModelProvider(this).get(TypeViewModel.class);

        TextView createTitle = findViewById(R.id.create_title);

        EditText dateInput = findViewById(R.id.popup_date_field);
        Spinner typeInput = findViewById(R.id.popup_type_spinner);
        Spinner catInput = findViewById(R.id.popup_category_spinner);
        EditText descInput = findViewById(R.id.popup_description_field);
        EditText partInput = findViewById(R.id.popup_part_field);
        CheckBox completeInput = findViewById(R.id.popup_complete_field);
        EditText notesInput = findViewById(R.id.popup_notes_field);

        Button createEdit = findViewById(R.id.create_entry_button);
        Button cancel = findViewById(R.id.cancel_entry_button);

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEntryActivity.this,
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
        categoryViewModel.getAllCategories().observe(CreateEntryActivity.this, categories -> {
            List<Category> categoriesWithAll = new ArrayList<>(); // new list which will contain a any category
            Category temp = new Category("Any");
            temp.id = -1;
            categoriesWithAll.add(temp);
            categoriesWithAll.addAll(categories);
            ArrayAdapter<Category> catAdapter = new ArrayAdapter<>(CreateEntryActivity.this, android.R.layout.simple_spinner_dropdown_item, categoriesWithAll);
            catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catInput.setAdapter(catAdapter);
        });

//        show type on spinner, show filtered if needed
        typeViewModel.getAllTypes().observe(this, types -> {
            int typeIndex = -1;
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).id == intent.getIntExtra("typeId", -1)){
                    typeIndex = i;
                    break;
                }
            }

            if (typeIndex >= 0) {
                typeInput.setSelection(typeIndex);
            }

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

                    ArrayAdapter<Type> typeAdapter = new ArrayAdapter<>(CreateEntryActivity.this, android.R.layout.simple_spinner_dropdown_item, filteredTypes);
                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    typeInput.setAdapter(typeAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    return;
                }
            });
        });

        if (isEdit) {
            createTitle.setText("Edit Entry");
            createEdit.setText("Edit");
            dateInput.setText(intent.getStringExtra("date"));
            descInput.setText(intent.getStringExtra("description"));
            notesInput.setText(intent.getStringExtra("notes"));
            partInput.setText(intent.getStringExtra("part"));
            completeInput.setChecked(intent.getBooleanExtra("isComplete", false));
        }

        createEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryViewModel_2 entryViewModel = new ViewModelProvider(CreateEntryActivity.this).get(EntryViewModel_2.class);

                String date = dateInput.getText().toString();
                Type type = (Type) typeInput.getSelectedItem();
                String desc = descInput.getText().toString();
                String part = partInput.getText().toString();
                boolean isComplete = completeInput.isChecked();
                String notes = notesInput.getText().toString();

//                TODO: add validation

                EntryDB newEntry = new EntryDB(date, type.id, desc, part, isComplete, notes);
                if (isEdit) {
                    newEntry.id = intent.getIntExtra("entryId",-1);
                    entryViewModel.updateEntry(newEntry);
                } else {
                    entryViewModel.insertEntry(newEntry);
                }
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    (Optional) Returning to the Previous Activity with Result
}

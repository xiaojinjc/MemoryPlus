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

    private ImageButton mainSettingButton;
    private FloatingActionButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageViewerSetup();

//        Open settings popup menu
        mainSettingButton = findViewById(R.id.button3);
        mainSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });

//        Go to create entry activity
        createButton = findViewById(R.id.createEntryFab);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showCreatePopup(null);
                startActivity(new Intent(MainActivity.this, CreateEntryActivity.class));
            }
        });

    }

//     Sets up the monthViewPager to display month fragments for each year
    private void pageViewerSetup() {
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

    }

//    Shows a popup of the settings
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

}
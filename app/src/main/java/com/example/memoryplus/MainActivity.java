package com.example.memoryplus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.adapters.MonthPagerAdapter;
import com.example.memoryplus.adapters.YearListAdapter;
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
import android.app.Dialog;
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
import android.widget.LinearLayout;
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

    private ImageButton mainSearchButton;
    private ImageButton mainSearchBackButton;
    private ImageButton mainTodayButton;
    private ImageButton mainSettingButton;
    private FloatingActionButton createButton;
    private EditText searchInput;
    private TextView yearDisplay;
    private LinearLayout monthDisplayContainer;
    private TextView monthDisplay;
    private String[] monthNames;
    private int currentYear;
    private List<Integer> yearList;

    private MonthPagerAdapter monthPagerAdapter;
    private ViewPager2 monthViewPager;
    private YearListAdapter yearListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        All items on layout
        mainSearchButton = findViewById(R.id.main_search);
        mainTodayButton = findViewById(R.id.main_today);
        mainSettingButton = findViewById(R.id.main_setting);
        createButton = findViewById(R.id.createEntryFab);
        yearDisplay = findViewById(R.id.toolbarYearText);
        monthDisplayContainer = findViewById(R.id.month_display_container);
        monthDisplay = findViewById(R.id.month_display);
        monthViewPager = findViewById(R.id.monthViewPager);

        yearList = new ArrayList<>();
        currentYear = Year.now().getValue(); // Default to this year
        for (int i = currentYear - 20; i <= currentYear + 20; i++){
            yearList.add(i);
        }
        yearListAdapter = new YearListAdapter(yearList, currentYear);

        monthPagerAdapter = new MonthPagerAdapter(this, currentYear);
        monthNames = new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };

        pageViewerSetup();


        mainSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

//        Jump to today
        mainTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthViewPager.setCurrentItem(LocalDate.now().getMonthValue() - 1, false);
                monthPagerAdapter.setYear(Year.now().getValue());
                yearDisplay.setText(Year.now().toString());
            }
        });

//        Open settings popup menu
        mainSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });

//        Go to create entry activity
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showCreatePopup(null);
                Intent intent = new Intent(MainActivity.this, CreateEntryActivity.class);
                intent.putExtra("mode", CreateEntryActivity.CreateMode.DEFAULT);
                startActivity(intent);
            }
        });

    }


//     Sets up the monthViewPager to display month fragments for each year
    private void pageViewerSetup() {
        yearDisplay.setText(String.valueOf(currentYear));

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
            int selectedYear = LocalDate.now().getYear(); // fallback

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


        yearDisplay.setOnClickListener(v -> {
            showYearDialog();
        });
    }

//    Shows the popup to select the year
    private void showYearDialog() {
        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_select_year, null);
        Context wrapper = new ContextThemeWrapper(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_AppCompat_Dark);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
        builder.setView(dialogView);
        builder.setTitle("Select Year");
        AlertDialog dialog = builder.create();

        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerViewYears);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        yearListAdapter.setOnClickListener(new YearListAdapter.OnClickListener() {
            @Override
            public void onYearClick(int selectedYear) {
                yearDisplay.setText(String.valueOf(selectedYear));
                yearListAdapter.setSelectedYear(selectedYear);
                Log.d("main year input", String.valueOf(selectedYear));
                monthPagerAdapter.setYear(selectedYear);
                monthViewPager.setAdapter(monthPagerAdapter);
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(yearListAdapter);

        currentYear = Integer.parseInt(yearDisplay.getText().toString());
        int scrollTo = yearList.indexOf(currentYear) - 1;
        recyclerView.scrollToPosition(scrollTo);

        dialog.show();
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
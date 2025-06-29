package com.example.memoryplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.viewmodel.EntryViewModel;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    private List<Entry> entries;
    private RecyclerView recyclerView;
//    private EntryAdapter adapter;
    private  EntryViewModel entryViewModel;
    private EntryGroupedAdapter adapter;

    private ImageButton mainSettingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.entryRecyclerView);
        adapter = new EntryGroupedAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        entryViewModel.getGroupedItems().observe(this, groupedItems -> {
            adapter.setItems(groupedItems);
        });


//        Open popup menu
        mainSettingButton = findViewById(R.id.button3);
        mainSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Toast.makeText(MainActivity.this,"Create Type is pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    return false;
                });

                settingPopup.show();
            }
        });

    }
}
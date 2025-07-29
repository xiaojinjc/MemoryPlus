package com.example.memoryplus;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.adapters.EntryGroupedAdapter;
import com.example.memoryplus.adapters.EntryGroupedAdapter_2;
import com.example.memoryplus.entities.EntryDB;
import com.example.memoryplus.entities.EntryWithType;
import com.example.memoryplus.items.EntryItem;
import com.example.memoryplus.items.HeaderItem;
import com.example.memoryplus.items.ListItem;
import com.example.memoryplus.viewmodels.EntryViewModel;
import com.example.memoryplus.viewmodels.EntryViewModel_2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MonthFragment extends Fragment {

    private static final String ARG_YEAR = "year";
    private static final String ARG_MONTH = "month";

    private EntryViewModel_2 entryViewModel;
    private RecyclerView recyclerView;
    private EntryGroupedAdapter_2 adapter;

    public static MonthFragment newInstance(int year, int month) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_YEAR, year);
        args.putInt(ARG_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerViewEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EntryGroupedAdapter_2();
        recyclerView.setAdapter(adapter);

        int year = requireArguments().getInt(ARG_YEAR);
        int month = requireArguments().getInt(ARG_MONTH);
        Log.d("Month", "mfer the month is "+ month +" and the year is " + year);

        entryViewModel = new ViewModelProvider(requireActivity()).get(EntryViewModel_2.class);

//        entryViewModel.getAllLive().observe(getViewLifecycleOwner(), list -> {
//            for (EntryDB e : list){
//                Log.d("Debug", "Entry: " + e.description + ", date: " + e.date);
//            }
//        });

        entryViewModel.getEntriesWithTypeForMonth(year, month)
                .observe(getViewLifecycleOwner(), entryWithTypes -> {
                    Log.d("MonthFragment", "Got " + entryWithTypes.size() + " entries for " + month + "/" + year);
                    List<ListItem> grouped = groupEntriesByDate(entryWithTypes);
                    adapter.setItems(grouped);
                });

        adapter.setOnEntryClickListener(entryDB -> {
            showEntryOptionPopup(entryDB);
        });
    }

    public List<ListItem> groupEntriesByDate(List<EntryWithType> entriesWithType) {
        List<ListItem> result = new ArrayList<>();
        String lastDate = null;

        DateTimeFormatter dbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (EntryWithType entry : entriesWithType) {
            String entryDate = entry.entryDB.date;
            if (!entryDate.equals(lastDate)) {
                LocalDate date = LocalDate.parse(entryDate, dbFormat);
                result.add(new HeaderItem(displayFormat.format(date), true, false));
                lastDate = entryDate;
            }
            result.add(new EntryItem(entry));
        }

        return result;
    }

    public void showEntryOptionPopup(EntryWithType entryWithType) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Options")
                .setItems(new CharSequence[]{"Edit", "Delete"}, (dialog, which) -> {
                    if (which == 0){
                        MainActivity ma = (MainActivity) requireActivity();
                        ma.showCreatePopup(entryWithType);
                    } else if (which == 1) {
                        entryViewModel.deleteEntry(entryWithType.entryDB);
                    }
                })
                .show();
    }
}

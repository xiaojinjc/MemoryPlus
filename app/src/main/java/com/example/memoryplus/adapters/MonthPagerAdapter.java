package com.example.memoryplus.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.memoryplus.MonthFragment;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class MonthPagerAdapter extends FragmentStateAdapter {
    private int selectedYear;

    public MonthPagerAdapter(@NonNull FragmentActivity fragmentActivity, int selectedYear) {
        super(fragmentActivity);
        this.selectedYear = selectedYear;
    }

    public void setYear(int year) {
        this.selectedYear = year;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        int year = startYear + (startMonth + position - 1) / 12;
//        int month = (startMonth + position - 1)  % 12 + 1;
//
//        MonthFragment fragment = new MonthFragment();
//        Bundle args = new Bundle();
//        args.putInt("year", year);
//        args.putInt("month", month);
//        fragment.setArguments(args);
//        return fragment;

//        YearMonth ym = months.get(position);
//        return MonthFragment.newInstance(ym.getYear(), ym.getMonthValue());

        int month = position + 1;
        return MonthFragment.newInstance(selectedYear, month);
    }

    @Override
    public int getItemCount() {
//        return months.size();
        return 12;
    }
}

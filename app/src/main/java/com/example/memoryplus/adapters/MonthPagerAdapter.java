package com.example.memoryplus.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.memoryplus.MonthFragment;

import java.time.YearMonth;

public class MonthPagerAdapter extends FragmentStateAdapter {
    private final int startYear = 2024;
    private final int startMonth = 1;
    private final int pageCount = 24;

    public MonthPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int year = startYear + (startMonth + position - 1) / 12;
        int month = (startMonth + position - 1)  % 12 + 1;

        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return pageCount;
    }

    public YearMonth getYearMonthAt(int position) {
        return YearMonth.of(startYear, startMonth).plusMonths(position);
    }
}

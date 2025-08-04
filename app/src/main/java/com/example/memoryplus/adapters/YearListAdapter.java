package com.example.memoryplus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.ViewHolder> {

    private final List<Integer> years;
    private final int currentYear;
    private final OnClickListener listener;

    public interface OnClickListener {
        void onYearClick(int year);
    }

    public YearListAdapter(List<Integer> years, int currentYear, OnClickListener listener) {
        this.years = years;
        this.currentYear = currentYear;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        tv.setPadding(32, 24, 32, 24);
        tv.setTextSize(18);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int year = years.get(position);
        holder.textView.setText(String.valueOf(year));
        holder.textView.setAlpha(year == currentYear ? 1f : 0.7f);
        holder.textView.setOnClickListener(v -> listener.onYearClick(year));
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}


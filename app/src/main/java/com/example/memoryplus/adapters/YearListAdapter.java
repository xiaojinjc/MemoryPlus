package com.example.memoryplus.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;

import java.util.List;

public class YearListAdapter extends RecyclerView.Adapter<YearListAdapter.ViewHolder> {

    private final List<Integer> yearList;
    private int selectedYear;
    private final OnClickListener listener;

    public interface OnClickListener {
        void onYearClick(int year);
    }

    public YearListAdapter(List<Integer> years, int currentYear, OnClickListener listener) {
        this.yearList = years;
        this.selectedYear = currentYear;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_year, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int year = yearList.get(position);
        holder.yearText.setText(String.valueOf(year));
        holder.yearText.setAlpha(year == selectedYear ? 1f : 0.7f);
        holder.yearText.setTextSize(year == selectedYear ? 30 : 25);
        holder.yearText.setOnClickListener(v -> {
            int previousSelected = selectedYear;
            selectedYear = year;
            notifyItemChanged(yearList.indexOf(previousSelected));
            notifyItemChanged(position);
            listener.onYearClick(year);
        });
    }

    @Override
    public int getItemCount() {
        return yearList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView yearText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            yearText = itemView.findViewById(R.id.year_item);
        }
    }
}


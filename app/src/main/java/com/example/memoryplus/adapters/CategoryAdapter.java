package com.example.memoryplus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> itemList = new ArrayList<>();
    private OnCategoryClickListener listener;

    public void setItemList(List<Category> newCategories){
        itemList = newCategories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category item = itemList.get(position);
        holder.text.setText(item.name);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(item);
                }
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEditClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageButton deleteButton;
        ImageButton editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.categoryText);
            deleteButton = itemView.findViewById(R.id.deleteCat);
            editButton = itemView.findViewById(R.id.edit_cat);
        }
    }

    public interface OnCategoryClickListener {
        void onDeleteClick(Category category);
        void onEditClick(Category category);
    }

    public void setOnCategoryClickListener (OnCategoryClickListener listener) {
        this.listener = listener;
    }
}

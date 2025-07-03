package com.example.memoryplus.adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.entities.TypeWithCategory;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder>{
    private List<TypeWithCategory> itemList = new ArrayList<>();

    public void setItemList(List<TypeWithCategory> newTypes){
        itemList = newTypes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TypeWithCategory item = itemList.get(position);
        holder.text.setText(item.type.name);
        holder.catText.setText(item.category.name);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView catText;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.typeText);
            catText = itemView.findViewById(R.id.typeCatText);
            deleteButton = itemView.findViewById(R.id.deleteType);
        }
    }
}

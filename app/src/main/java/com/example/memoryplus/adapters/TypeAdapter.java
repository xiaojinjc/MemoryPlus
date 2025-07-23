package com.example.memoryplus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memoryplus.R;
import com.example.memoryplus.entities.TypeWithCategory;
import com.example.memoryplus.entities.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder>{
    private List<TypeWithCategory> itemList = new ArrayList<>();
    private TypeAdapter.OnTypeClickListener listener;

    public void setItemList(List<TypeWithCategory> newTypes){
        itemList = newTypes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TypeWithCategory item = itemList.get(position);
        holder.text.setText(item.type.name);
        holder.catText.setText(item.category.name);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onDeleteClick(item);
                }
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUpdateClick(item.type);
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
        TextView catText;
        ImageButton deleteButton;
        ImageButton editButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.typeText);
            catText = itemView.findViewById(R.id.typeCatText);
            deleteButton = itemView.findViewById(R.id.deleteType);
            editButton = itemView.findViewById(R.id.edit_type);
        }
    }

    public interface OnTypeClickListener {
        void onDeleteClick(TypeWithCategory typeWithCategory);
        void onUpdateClick(Type type);
    }

    public void setOnTypeClickListener (TypeAdapter.OnTypeClickListener listener) {
        this.listener = listener;
    }
}

package com.example.memoryplus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.memoryplus.entity.Category;
import com.example.memoryplus.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository repository;
    LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoryRepository(application);
        allCategories = (LiveData<List<Category>>) repository.getAll();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insertCategory(Category category) {
        repository.insert(category);
    }

    public void deleteCategory(Category category) {
        repository.delete(category);
    }
}

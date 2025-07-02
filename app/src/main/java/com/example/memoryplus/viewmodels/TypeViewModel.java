package com.example.memoryplus.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.memoryplus.entities.Category;
import com.example.memoryplus.entities.Type;
import com.example.memoryplus.repositories.TypeRepository;

import java.util.List;

public class TypeViewModel extends AndroidViewModel {
    private final TypeRepository repository;
    LiveData<List<Type>> allTypes;

    public TypeViewModel(@NonNull Application application) {
        super(application);
        repository = new TypeRepository(application);
        allTypes = (LiveData<List<Type>>) repository.getAll();
    }

    public LiveData<List<Type>> getAllTypes() { return allTypes; }

    public void insertType(Type type) { repository.insert(type); }

    public void deleteType(Type type) { repository.delete(type); }

//    TODO: add method for getTypeListByCat, Livedata?
}

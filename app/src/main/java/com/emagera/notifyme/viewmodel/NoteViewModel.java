package com.emagera.notifyme.viewmodel;

import android.app.Application;

import com.emagera.notifyme.entity.Note;
import com.emagera.notifyme.repository.NoteRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note)
    {
        repository.insert(note);
    }

    public void update(Note note)
    {
        repository.insert(note);
    }

    public void delete(Note note)
    {
        repository.insert(note);
    }

    public void deleteAllNotes()
    {
        repository.deletAllNotes();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }
}

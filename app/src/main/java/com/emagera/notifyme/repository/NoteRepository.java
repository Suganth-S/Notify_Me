package com.emagera.notifyme.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.emagera.notifyme.dao.NoteDao;
import com.emagera.notifyme.db.NoteDataBase;
import com.emagera.notifyme.entity.Note;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    //we need to pass our application, and since application is our context
    public NoteRepository (Application application)
    {
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
       //noteDao is called becoz its abstract, but normally we cant call abstract method without body,
        // here it becomes , because we had a instance, Room subclasses our abstract class
        noteDao = dataBase.noteDao();
        allNotes = noteDao.getAllNote();
    }

    //room doesn't allwo background task on main task, since could allow this, we are going on with Asynctask
    public void insert (Note note)
    {
        new InsertNoteAsynctask(noteDao).execute(note);
    }

    public void update (Note note)
    {
        new UpdateNoteAsynctask(noteDao).execute(note);
    }

    public void delete (Note note)
    {
        new DeleteNoteAsynctask(noteDao).execute(note);
    }

    public void deletAllNotes ()
    {
        new DeleteAllNoteAsynctask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }

    public static class InsertNoteAsynctask extends AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private InsertNoteAsynctask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateNoteAsynctask extends AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private UpdateNoteAsynctask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsynctask extends AsyncTask<Note, Void, Void>
    {
        private NoteDao noteDao;

        private DeleteNoteAsynctask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public static class DeleteAllNoteAsynctask extends AsyncTask<Void, Void, Void>
    {
        private NoteDao noteDao;

        private DeleteAllNoteAsynctask(NoteDao noteDao)
        {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}

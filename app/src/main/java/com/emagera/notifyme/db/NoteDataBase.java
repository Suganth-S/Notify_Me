package com.emagera.notifyme.db;

import android.content.Context;
import android.os.AsyncTask;

import com.emagera.notifyme.dao.NoteDao;
import com.emagera.notifyme.entity.Note;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//we can also add more than 1 entity inside a {}
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    //we are creating singleton, becoz no need to create a many instance instead we can use this instance everywhere
    private static NoteDataBase instance;

    public abstract NoteDao noteDao();

    //synchronized means only one thread can access this thread at a time, if multithreading happens means this helps to avoid this
    public static synchronized NoteDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class,
                    //when we increase the direction of database we need to tell the Room DB to how to migrate, so we use "fallbackToDestructiveMigration"
                    "note_database").fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynctask(instance).execute();
        }
    };

    private static class PopulateDbAsynctask extends AsyncTask<Void, Void, Void>
    {
        private NoteDao noteDao;

        private PopulateDbAsynctask(NoteDataBase db)
        {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("title 1", "description 1", 1));
            noteDao.insert(new Note("title 2", "description 2", 2));
            noteDao.insert(new Note("title 3", "description 3", 3));
            return null;
        }
    }
}

package com.emagera.notifyme.dao;

import com.emagera.notifyme.entity.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    public void insert(Note note);

    @Update
    public void update (Note note);

    @Delete
    public void delete (Note note);

    @Query("DELETE FROM note_table")
    public void deleteAllNotes();

    /*we used Live Data is becoz it keep on looking the Note object, and update automaticallly if there
    is anychange in the Note obj, so we no need to do anything */

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNote();
}

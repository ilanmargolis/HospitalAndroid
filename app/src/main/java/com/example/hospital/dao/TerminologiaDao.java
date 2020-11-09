package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Terminologia;
import com.example.hospital.model.Unidade;

import java.util.List;

@Dao
public interface TerminologiaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Terminologia> terminologiaList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Terminologia terminologia);

    @Update
    public void update(Terminologia terminologia);

    @Query("SELECT * FROM Terminologia")
    public List<Terminologia> getAll();

    @Query("SELECT * FROM Terminologia WHERE id = :id")
    public Terminologia getTerminologia(long id);

    @Delete
    public void delete(Terminologia terminologia);
}

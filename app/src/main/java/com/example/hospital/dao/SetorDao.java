package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Setor;

import java.util.List;

@Dao
public interface SetorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Setor> setorList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Setor setor);

    @Update
    public void update(Setor setor);

    @Query("SELECT * FROM Setor")
    public List<Setor> getAll();

    @Query("SELECT * FROM Setor WHERE id = :id")
    public Setor get(int id);

    @Delete
    public void delete(Setor setor);
}




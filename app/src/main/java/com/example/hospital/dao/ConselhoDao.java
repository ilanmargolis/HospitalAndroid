package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Conselho;

import java.util.List;

@Dao
public interface ConselhoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Conselho> conselhoList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Conselho conselho);

    @Update
    public void update(Conselho conselho);

    @Query("SELECT * FROM Conselho")
    public List<Conselho> getAll();

    @Query("SELECT * FROM Conselho WHERE id = :id")
    public Conselho get(long id);

    @Delete
    public void delete(Conselho conselho);
}

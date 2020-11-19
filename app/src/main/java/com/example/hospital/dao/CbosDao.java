package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Cbos;

import java.util.List;

@Dao
public interface CbosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Cbos> cbosList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Cbos cbos);

    @Update
    public void update(Cbos cbos);

    @Query("SELECT * FROM Cbos")
    public List<Cbos> getAll();

    @Query("SELECT * FROM Cbos WHERE codigo = :codigo")
    public Cbos getByCodigo(String codigo);

    @Delete
    public void delete(Cbos cbos);
}

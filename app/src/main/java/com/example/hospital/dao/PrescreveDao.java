package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Prescreve;

import java.util.List;

@Dao
public interface PrescreveDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Prescreve prescreve);

    @Update
    public void update(Prescreve prescreve);

    @Query("SELECT * FROM Prescreve")
    public List<Prescreve> getAll();

    @Query("SELECT * FROM Prescreve WHERE internado_id = :internado_id")
    public List<Prescreve> getByInternado(long internado_id);

    @Delete
    public void delete(Prescreve prescreve);
}

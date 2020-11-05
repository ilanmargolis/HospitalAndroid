package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.hospital.model.Terminologia;

import java.util.List;

@Dao
public interface TerminologiaDao {

    @Query("SELECT * FROM Terminologia")
    public List<Terminologia> getAll();
}

package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Medico;

import java.util.List;

@Dao
public interface MedicoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Medico> medicoList);

    @Update
    public void update(Medico medico);

    @Query("SELECT * FROM Medico")
    public List<Medico> getAll();

    @Query("SELECT * FROM Medico WHERE email = :email")
    public Medico getMedico(String email);

    @Delete
    public void delete(Medico medico);
}

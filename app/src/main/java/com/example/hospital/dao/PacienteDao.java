package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;

import java.util.List;

@Dao
public interface PacienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Paciente> pacienteList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Paciente paciente);

    @Update
    public void update(Paciente paciente);

    @Query("SELECT * FROM Paciente")
    public List<Paciente> getAll();

    @Delete
    public void delete(Paciente paciente);
}

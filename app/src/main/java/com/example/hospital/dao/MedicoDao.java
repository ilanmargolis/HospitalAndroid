package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Funcionario;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medico;

import java.util.List;

@Dao
public interface MedicoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Medico> medicoList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Medico medico);

    @Update
    public void update(Medico medico);

    @Query("SELECT * FROM Medico")
    public List<Medico> getAll();

    @Query("SELECT * FROM Medico WHERE id = :id")
    public Medico getById(long id);

    @Query("SELECT * FROM Medico WHERE email = :email")
    public Medico getByEmail(String email);

    @Delete
    public void delete(Medico medico);
}

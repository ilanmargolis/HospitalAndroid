package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Medicamento;

import java.util.List;

@Dao
public interface MedicamentoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Medicamento> medicamentoList);

    @Update
    public void update(Medicamento medicamento);

    @Query("SELECT * FROM Medicamento")
    public List<Medicamento> getAll();

    @Delete
    public void delete(Medicamento medicamento);
}

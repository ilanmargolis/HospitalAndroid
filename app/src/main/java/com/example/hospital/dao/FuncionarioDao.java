package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Funcionario;

import java.util.List;

@Dao
public interface FuncionarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Funcionario> funcionarioList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Funcionario funcionario);

    @Update
    public void update(Funcionario funcionario);

    @Query("SELECT * FROM Funcionario")
    public List<Funcionario> getAll();

    @Query("SELECT * FROM Funcionario WHERE id = :id")
    public Funcionario get(long id);

    @Query("SELECT * FROM Funcionario WHERE email = :email")
    public Funcionario getByEmail(String email);

    @Delete
    public void delete(Funcionario funcionario);
}




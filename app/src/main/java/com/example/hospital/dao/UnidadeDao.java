package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hospital.model.Unidade;

import java.util.List;

@Dao
public interface UnidadeDao {

    @Query("SELECT * FROM Unidade")
    public List<Unidade> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertAll(List<Unidade> professorList);
}

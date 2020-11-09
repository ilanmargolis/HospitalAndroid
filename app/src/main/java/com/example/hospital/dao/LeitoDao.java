package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Leito;

import java.util.List;

@Dao
public interface LeitoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Leito> leitoList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Leito leito);

    @Update
    public void update(Leito leito);

    @Query("SELECT * FROM Leito")
    public List<Leito> getAll();

    @Query("SELECT * FROM Leito WHERE id = :id")
    public Leito getLeito(long id);

    @Delete
    public void delete(Leito leito);
}

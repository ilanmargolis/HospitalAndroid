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
public interface InternarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Leito> leitoList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Leito leito);

    @Update
    public void update(Leito leito);

    @Query("SELECT * FROM Leito")
    public List<Leito> getAll();

    @Query("SELECT * FROM Internado I, Unidade U, Setor S WHERE U.id = :unidade_id and S.id = :setor")
    public Leito getLeito(long unidade_id, long setor_id);

    @Delete
    public void delete(Leito leito);
}

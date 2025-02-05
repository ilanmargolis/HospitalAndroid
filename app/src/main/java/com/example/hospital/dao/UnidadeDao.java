package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Unidade;

import java.util.List;

@Dao
public interface UnidadeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Unidade> unidadeList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Unidade unidade);

    @Update
    public void update(Unidade unidade);

    @Query("SELECT * FROM Unidade")
    public List<Unidade> getAll();

    @Query("SELECT * FROM Unidade WHERE id = :id")
    public Unidade getById(long id);

    @Query("SELECT * FROM Unidade U " +
            "INNER JOIN leito L ON L.unidade_id = U.id AND U.id = :unidade_id")
    public List<Unidade> getLeitosUnidade(long unidade_id);

    @Delete
    public void delete(Unidade unidade);
}




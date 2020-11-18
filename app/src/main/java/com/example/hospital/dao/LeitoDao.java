package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Leito;
import com.example.hospital.model.Setor;
import com.example.hospital.model.Unidade;

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

    @Query("SELECT L.* FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidadeid = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setorid = S.id " +
            "WHERE NOT EXISTS (SELECT leito_id FROM Internado WHERE leito_id = L.id)")
    public List<Leito> getLeitoUnidadeSetor(long unidade_id, long setor_id);

    @Delete
    public void delete(Leito leito);
}

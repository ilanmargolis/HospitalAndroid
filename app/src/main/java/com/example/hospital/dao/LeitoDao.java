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
    public Leito getById(long id);

    @Query("SELECT DISTINCT(L.id), L.* FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id " +
            "LEFT OUTER JOIN internado I ON I.leito_id = L.id " +
            "LEFT OUTER JOIN alta A on A.internado_id = I.id " +
            "WHERE I.id is null OR " +
            "I.id in (SELECT internado_id FROM alta A)")
    //            "WHERE I.id IN (SELECT internado_id FROM alta) OR " +
//            "NOT EXISTS (SELECT leito_id FROM Internado WHERE leito_id = L.id)")
//    LEFT OUTER join internado I on I.leito_id = L.id
//    LEFT OUTER JOIN alta A on A.id_internado = I.id
//    where I.id is null or
//    I.id in (SELECT id_internado FROM alta A);
    public List<Leito> getLeitosDisponiveis(long unidade_id, long setor_id);

    @Delete
    public void delete(Leito leito);
}

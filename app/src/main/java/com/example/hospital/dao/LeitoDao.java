package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Leito;
import com.example.hospital.model.Setor;

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

    @Query("SELECT * FROM Leito WHERE setor_id = :setor_id")
    public List<Leito> getBySetor(long setor_id);

    @Query("SELECT * FROM Leito L " +
            "INNER JOIN internado I ON I.leito_id = L.id " +
            "WHERE L.id = :id")
            public List<Leito>getInternamentoLeito(long id);

    @Query("SELECT DISTINCT L.id, L.* FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id ")
    public List<Leito> getLeitosUnidadeSetor(long unidade_id, long setor_id);

    @Query("SELECT DISTINCT L.id, L.* FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id " +
            "LEFT OUTER JOIN internado I ON I.leito_id = L.id " +
            "WHERE I.id IS NULL ")
    public List<Leito> getLeitosDesocupados(long unidade_id, long setor_id);

    @Query("SELECT DISTINCT L.id, L.* FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id " +
            "INNER JOIN internado I ON I.leito_id = L.id " +
            "LEFT OUTER JOIN alta A on A.internado_id = I.id " +
            "WHERE A.id IS NULL;")
    public List<Leito> getLeitosOcupados(long unidade_id, long setor_id);

    @Delete
    public void delete(Leito leito);
}

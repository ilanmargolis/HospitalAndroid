package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;

import java.util.List;

@Dao
public interface InternarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Internado> internadoList);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Internado internado);

    @Update
    public void update(Internado internado);

    @Query("SELECT * FROM Internado")
    public List<Internado> getAll();

    @Query("SELECT I.* FROM internado I " +
            "INNER JOIN leito L ON L.id = I.leito_id " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id")
    public List<Internado> getInternadoUnidadeSetor(long unidade_id, long setor_id);

    @Query("SELECT DISTINCT(L.id), * FROM leito L " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id " +
            "INNER JOIN internado I ON I.leito_id = L.id " +
            "LEFT OUTER JOIN alta A ON A.internado_id = I.id")
//            "OUTER JOIN alta A ON I.id = A.internado_id " +
//            "WHERE I.id NOT IN (SELECT internado_id FROM alta)")
    public List<Internado> getInternadoPaciente(long unidade_id, long setor_id);

    @Delete
    public void delete(Internado internado);
}

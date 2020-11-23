package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Internado;

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

    @Query("SELECT I.* FROM internado I " +
            "INNER JOIN leito L ON I.leito_id = L.id " +
            "INNER JOIN unidade U ON U.id = :unidade_id and L.unidade_id = U.id " +
            "INNER JOIN setor S ON S.id = :setor_id and L.setor_id = S.id " +
            "LEFT OUTER JOIN alta A ON A.internado_id = I.id " +
            "WHERE A.id IS NULL")
    public List<Internado> getInternadoPacientes(long unidade_id, long setor_id);

    @Query("SELECT I.* FROM internado I " +
            "INNER JOIN leito L ON I.leito_id = L.id " +
            "INNER JOIN unidade U ON L.unidade_id = U.id " +
            "INNER JOIN setor S ON L.setor_id = S.id " +
            "INNER JOIN paciente ON I.paciente_id = :paciente_id " +
            "LEFT OUTER JOIN alta A ON A.internado_id = I.id " +
            "WHERE A.id IS NULL")
    public boolean isInternadoPaciente(long paciente_id);

    @Delete
    public void delete(Internado internado);
}

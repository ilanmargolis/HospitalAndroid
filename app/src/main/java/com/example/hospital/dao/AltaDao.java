package com.example.hospital.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hospital.model.Alta;
import com.example.hospital.model.Internado;

import java.util.List;

@Dao
public interface AltaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Alta alta);

    @Update
    public void update(Alta alta);

    @Query("SELECT * FROM Alta")
    public List<Alta> getAll();

    @Query("SELECT * FROM Alta WHERE internado_id = :internado_id")
    public Alta getById(long internado_id);

    @Delete
    public void delete(Alta alta);
}

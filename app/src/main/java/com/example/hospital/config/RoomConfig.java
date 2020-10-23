package com.example.hospital.config;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.hospital.dao.UnidadeDao;
import com.example.hospital.model.Unidade;

@Database(entities = {Unidade.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RoomConfig extends RoomDatabase {

    private static RoomConfig instance = null;

    public abstract UnidadeDao unidadeDAO();

    public static RoomConfig getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    RoomConfig.class,
                    "hospital.db")
                    .allowMainThreadQueries() //Permite que o room rode na main principal
                    .fallbackToDestructiveMigration()  // receia o database se necessário
                    .build();
        }

        return instance;
    }
}
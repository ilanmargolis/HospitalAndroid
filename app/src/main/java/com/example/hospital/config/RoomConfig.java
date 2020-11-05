package com.example.hospital.config;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.hospital.dao.LeitoDao;
import com.example.hospital.dao.MedicoDao;
import com.example.hospital.dao.UnidadeDao;
import com.example.hospital.model.Conselho;
import com.example.hospital.model.Internado;
import com.example.hospital.model.Leito;
import com.example.hospital.model.Medicamento;
import com.example.hospital.model.Medico;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.Prescreve;
import com.example.hospital.model.Terminologia;
import com.example.hospital.model.Unidade;

@Database(entities = {Conselho.class, Internado.class, Leito.class, Medicamento.class, Medico.class,
        Paciente.class, Prescreve.class, Terminologia.class, Unidade.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RoomConfig extends RoomDatabase {

    private static RoomConfig instance = null;

    public abstract UnidadeDao unidadeDao();

    public abstract LeitoDao leitoDao();

    public abstract MedicoDao medicoDao();

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